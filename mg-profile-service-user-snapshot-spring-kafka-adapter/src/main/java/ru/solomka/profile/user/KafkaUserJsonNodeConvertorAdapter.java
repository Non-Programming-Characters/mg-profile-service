package ru.solomka.profile.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.solomka.profile.common.JsonNodeConvertor;
import ru.solomka.profile.user.entity.KafkaUserEntity;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class KafkaUserJsonNodeConvertorAdapter implements JsonNodeConvertor<KafkaUserEntity> {

    @SneakyThrows
    @Override
    public KafkaUserEntity convertFromNode(Class<KafkaUserEntity> clazz, JsonNode node) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());

        return objectMapper.treeToValue(node, clazz);
    }
}