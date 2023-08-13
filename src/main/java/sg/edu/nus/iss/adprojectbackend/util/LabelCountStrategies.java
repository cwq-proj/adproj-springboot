package sg.edu.nus.iss.adprojectbackend.util;

import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordCacheDTO;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;

public class LabelCountStrategies {

    public interface LabelCountStrategy{
        public long getCount(HealthRecordDTO healthRecordDTO);
        public void updateCounts(HealthRecordCacheDTO healthRecordDTO, long trueCount, long falseCount);
    }

    public static class MaleLabelCountStrategy implements LabelCountStrategy{

        @Override
        public long getCount(HealthRecordDTO healthRecordDTO) {
            return healthRecordDTO.getMale() == 1 ? 1 : 0;
        }

        @Override
        public void updateCounts(HealthRecordCacheDTO healthRecordDTO, long trueCount, long falseCount) {
            healthRecordDTO.getMale().setTrueLabelCount((int) trueCount);
            healthRecordDTO.getMale().setFalseLabelCount((int) falseCount);
        }
    }

    public static class CHDCountStrategy implements LabelCountStrategy{

        @Override
        public long getCount(HealthRecordDTO healthRecordDTO) {
            return healthRecordDTO.getTenYearCHD() == 1 ? 1 : 0;
        }

        @Override
        public void updateCounts(HealthRecordCacheDTO healthRecordDTO, long trueCount, long falseCount) {
            healthRecordDTO.getTenYearCHD().setTrueLabelCount((int) trueCount);
            healthRecordDTO.getTenYearCHD().setFalseLabelCount((int) falseCount);
        }
    }
}
