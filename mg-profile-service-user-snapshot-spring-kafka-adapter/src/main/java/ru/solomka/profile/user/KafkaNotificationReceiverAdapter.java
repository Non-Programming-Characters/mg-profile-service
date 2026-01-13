package ru.solomka.profile.user;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.solomka.profile.common.JsonNodeConvertor;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.user.entity.KafkaUserEntity;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaNotificationReceiverAdapter {

    @NonNull ProfileService profileService;

    @NonNull JsonNodeConvertor<KafkaUserEntity> kafkaUserEntityJsonNodeConvertor;

    @KafkaListener(topics = KafkaUserNotificationReceiveTopicPoints.CREATE_USER_EVENT_TOPIC)
    public ProfileEntity receiveUserCreate(@Payload JsonNode payload,
                                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        KafkaUserEntity userEntity = kafkaUserEntityJsonNodeConvertor.convertFromNode(KafkaUserEntity.class, payload);
        return profileService.create(
                ProfileEntity.builder()
                        .id(userEntity.getId())
                        .userTag(userEntity.getLogin())
                        .firstName(userEntity.getLogin())
                        .lastName(null)
                        .email(userEntity.getEmail())
                        .lastEditedAt(Instant.now())
                        .createdAt(userEntity.getCreatedAt())
                        .build()
        );
    }

    @KafkaListener(topics = KafkaUserNotificationReceiveTopicPoints.UPDATE_USER_EVENT_TOPIC)
    public ProfileEntity receiveUserUpdate(@Payload JsonNode payload,
                                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        KafkaUserEntity kafkaUserEntity = kafkaUserEntityJsonNodeConvertor.convertFromNode(KafkaUserEntity.class, payload);
        ProfileEntity profileEntity = profileService.getById(kafkaUserEntity.getId());
        profileEntity.setEmail(kafkaUserEntity.getEmail());
        profileEntity.setLastEditedAt(Instant.now());
        return profileService.update(profileEntity);
    }

    @KafkaListener(topics = KafkaUserNotificationReceiveTopicPoints.DELETE_USER_EVENT_TOPIC)
    public ProfileEntity receiveUserDelete(@Payload JsonNode payload,
                                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        KafkaUserEntity kafkaUserEntity = kafkaUserEntityJsonNodeConvertor.convertFromNode(KafkaUserEntity.class, payload);
        return profileService.deleteById(kafkaUserEntity.getId());
    }
}