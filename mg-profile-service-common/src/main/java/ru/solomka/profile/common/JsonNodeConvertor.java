package ru.solomka.profile.common;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonNodeConvertor<R> {

    R convertFromNode(Class<R> clazz, JsonNode node);
}