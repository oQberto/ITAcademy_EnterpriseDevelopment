package util;

import by.alex.mobile_operator.entity.plan.Plan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Plan> map(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<>() {});
    }
}
