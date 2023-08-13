package sg.edu.nus.iss.adprojectbackend.mapper;

import org.springframework.stereotype.Component;
import sg.edu.nus.iss.adprojectbackend.dto.SensitiveInfoDTO;

import java.util.HashMap;
import java.util.Map;

// This code is to demo how to use model mapper for custom property mappings
@Component
public class SensitiveInfoMapper {

    public static Map<String, Object> toFormattedMap(SensitiveInfoDTO sensitiveInfoDTO) {
        Map<String, Object> formattedData = new HashMap<>();
        formattedData.put("id", sensitiveInfoDTO.getId());
        formattedData.put("nric", sensitiveInfoDTO.getNric());
        formattedData.put("firstName", sensitiveInfoDTO.getFirstName());
        formattedData.put("lastName", sensitiveInfoDTO.getLastName());
        formattedData.put("phoneNumber", sensitiveInfoDTO.getPhoneNumber());
        formattedData.put("userId", sensitiveInfoDTO.getUserId());
        formattedData.put("healthRecord_Id", sensitiveInfoDTO.getHealthRecord());
        formattedData.put("createdDate", sensitiveInfoDTO.getCreatedDate());

        return formattedData;
    }
}
