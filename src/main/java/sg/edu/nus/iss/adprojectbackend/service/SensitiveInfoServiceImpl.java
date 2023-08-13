package sg.edu.nus.iss.adprojectbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.SensitiveInfoDTO;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;
import sg.edu.nus.iss.adprojectbackend.repository.SensitiveInfoRepository;

@Service
public class SensitiveInfoServiceImpl implements SensitiveInfoService {
    private final SensitiveInfoRepository sensitiveInfoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensitiveInfoServiceImpl(SensitiveInfoRepository sensitiveInfoRepository,
                                    ModelMapper modelMapper) {
        this.sensitiveInfoRepository = sensitiveInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<SensitiveInfoDTO> getSensitiveInfoList() {
        Flux<SensitiveInfo> sensitiveInfoList = sensitiveInfoRepository.findAll();
        return sensitiveInfoList.map((info) -> modelMapper.map(info, SensitiveInfoDTO.class));
    }

    @Override
    public Mono<SensitiveInfoDTO> findByNricAndFirstNameAndLastName(String nric, String firstName, String lastName) {
        return sensitiveInfoRepository.findByNricAndFirstNameAndLastName(nric, firstName, lastName)
                .flatMap(info -> Mono.just(modelMapper.map(info, SensitiveInfoDTO.class)))
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<SensitiveInfoDTO> save(Mono<SensitiveInfoDTO> sensitiveInfoDTO) {
        return sensitiveInfoDTO
                .map(infoDTO -> modelMapper.map(infoDTO, SensitiveInfo.class))
                .flatMap(info -> sensitiveInfoRepository.save(info))
                .map(savedInfo -> modelMapper.map(savedInfo, SensitiveInfoDTO.class));
    }

    @Override
    public Mono<SensitiveInfoDTO> findById(String id) {
        return sensitiveInfoRepository.findById(id)
                .flatMap(info -> Mono.just(modelMapper.map(info, SensitiveInfoDTO.class)));
    }


}
