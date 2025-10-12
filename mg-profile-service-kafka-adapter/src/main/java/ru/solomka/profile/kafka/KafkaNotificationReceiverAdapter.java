package ru.solomka.profile.kafka;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.solomka.profile.kafka.event.KafkaUserCreatedEvent;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaNotificationReceiverAdapter {

    @NonNull ProfileService profileService;

    @KafkaListener(topics = "mg-identity-service_create-user-event-topic", groupId = "create-user-group", containerFactory = "kafkaListenerContainerFactory")
    public ProfileEntity receiveCreate(@Payload KafkaUserCreatedEvent userCreatedEvent,
                                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        return profileService.create(
                ProfileEntity.builder()
                        .id(userCreatedEvent.getFirstChapterNotifyMessage().getId())
                        .createdAt(Instant.now())
                        .build()
        );
    }
}