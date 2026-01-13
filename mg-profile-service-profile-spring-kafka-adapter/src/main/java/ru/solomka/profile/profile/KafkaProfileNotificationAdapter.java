package ru.solomka.profile.profile;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import ru.solomka.profile.common.EntityNotification;
import ru.solomka.profile.profile.event.KafkaProfileEvent;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProfileNotificationAdapter implements EntityNotification<ProfileEntity> {

    @NonNull KafkaTemplate<String, KafkaProfileEvent> kafkaProfileEventKafkaTemplate;

    @Override
    public void notifyCreate(ProfileEntity entity) {
        kafkaProfileEventKafkaTemplate.send(
                KafkaProfileNotificationTopicPoints.CREATE_PROFILE_EVENT_TOPIC,
                new KafkaProfileEvent(
                        entity.getId(), entity.getUserTag(),
                        entity.getFirstName(), entity.getLastName(),
                        entity.getEmail(), entity.getCreatedAt()
                )
        );
    }

    @Override
    public void notifyUpdate(ProfileEntity entity) {
        kafkaProfileEventKafkaTemplate.send(
                KafkaProfileNotificationTopicPoints.UPDATE_PROFILE_EVENT_TOPIC,
                new KafkaProfileEvent(
                        entity.getId(), entity.getUserTag(),
                        entity.getFirstName(), entity.getLastName(),
                        entity.getEmail(), entity.getCreatedAt()
                )
        );
    }

    @Override
    public void notifyDelete(ProfileEntity entity) {
        kafkaProfileEventKafkaTemplate.send(
                KafkaProfileNotificationTopicPoints.DELETE_PROFILE_EVENT_TOPIC,
                new KafkaProfileEvent(
                        entity.getId(), entity.getUserTag(),
                        entity.getFirstName(), entity.getLastName(),
                        entity.getEmail(), entity.getCreatedAt()
                )
        );
    }
}