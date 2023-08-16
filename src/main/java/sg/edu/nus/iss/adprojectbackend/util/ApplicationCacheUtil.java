package sg.edu.nus.iss.adprojectbackend.util;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.ApplicationCacheDTO;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordCacheDTO;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ApplicationCacheUtil {

    private static final int NUMDATA = 50;

    public static Mono<List<Double>> getMinMaxIntervals(Flux<HealthRecordDTO> healthRecordDTOFlux) {
        return healthRecordDTOFlux
                .map(HealthRecordDTO::getAge)
                .collectList()
                .map(dfList -> {
                    double minVal = dfList.stream().mapToDouble(value -> value.doubleValue()).min().orElse(0);
                    double maxVal = dfList.stream().mapToDouble(value -> value.doubleValue()).max().orElse(0);
                    double valIntervalWidth = (maxVal - minVal) / NUMDATA;

                    double xdataMin = Math.max(minVal - 10 * valIntervalWidth, 0);
                    double xdataMax = maxVal + 10 * valIntervalWidth;
                    double intervalWidth = (xdataMax - xdataMin) / NUMDATA;

                    List<Double> result = new ArrayList<>();
                    result.add(xdataMin);
                    result.add(xdataMax);
                    result.add(intervalWidth);

                    return result;
                });
    }

    public static Mono<ApplicationCacheDTO> updateHealthRecordCacheAge(
            Mono<ApplicationCacheDTO> applicationDTOMono,
            Flux<HealthRecordDTO> healthRecordDTOFlux) {
        return getMinMaxIntervals(healthRecordDTOFlux)
                .flatMap(results -> {
                    double xdataMin = results.get(0);
                    double xdataMax = results.get(1);
                    double intervalWidth = results.get(2);

                    List<Double> averageValues = new ArrayList<>();

                    return Flux.range(0, NUMDATA)
                            .flatMap(i -> {
                                double intervalStart = xdataMin + i * intervalWidth;
                                double intervalEnd = intervalStart + intervalWidth;

                                double intervalAverage = intervalStart + (intervalEnd - intervalStart) / 2;
                                averageValues.add(intervalAverage);

                                // Count occurrences within the interval asynchronously
                                return healthRecordDTOFlux
                                        .filter(value -> intervalStart <= value.getAge()
                                                && value.getAge() < intervalEnd && value.getAge()>0)
                                        .count()
                                        .map(count -> (double) count);
                            })
                            .collectList()
                            .flatMap(occurrences -> {
                                return applicationDTOMono.flatMap(applicationDTO -> {
                                    HealthRecordCacheDTO healthRecordCacheDTO = applicationDTO.getHealthRecordCacheDTO();
                                    healthRecordCacheDTO.getAge().setXData(averageValues);
                                    healthRecordCacheDTO.getAge().setYCount(occurrences);

                                    return Mono.just(applicationDTO);
                                });
                            });
                });
    }

    public static Mono<ApplicationCacheDTO> updateHealthRecordCacheBinary(Mono<ApplicationCacheDTO> applicationDTOMono,
                                                                    Flux<HealthRecordDTO> healthRecordsDTO,
                                                                    LabelCountStrategies.LabelCountStrategy labelCountStrategy) {
        return applicationDTOMono
                .flatMap(applicationDTO -> {
                    HealthRecordCacheDTO healthRecordDTO = applicationDTO.getHealthRecordCacheDTO();

                    // Get the true count
                    Mono<Long> trueCountMono = healthRecordsDTO
                            .map(labelCountStrategy::getCount)
                            .filter(count -> count == 1)
                            .count();

                    // Get the false count
                    Mono<Long> falseCountMono = healthRecordsDTO
                            .map(labelCountStrategy::getCount)
                            .filter(count -> count == 0)
                            .count();

                    // Combine true and false counts and update the DTO
                    return Mono.zip(trueCountMono, falseCountMono)
                            .map(tuple -> {
                                long trueCount = tuple.getT1();
                                long falseCount = tuple.getT2();

                                labelCountStrategy.updateCounts(healthRecordDTO, trueCount, falseCount);

                                // Set the health record into the application cache
                                applicationDTO.setHealthRecordCacheDTO(healthRecordDTO);
                                return applicationDTO;
                            });
                });
    }


}
