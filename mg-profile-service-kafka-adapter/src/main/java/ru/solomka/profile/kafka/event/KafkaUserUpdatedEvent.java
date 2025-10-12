package ru.solomka.profile.kafka.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.principal.PrincipalEntity;
import ru.solomka.profile.user.UserSnapshotEntity;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Setter @Getter
public class KafkaUserUpdatedEvent extends KafkaEvent<UserSnapshotEntity, PrincipalEntity> {
    @NonNull UserSnapshotEntity firstChapterNotifyMessage;
    @NonNull PrincipalEntity secondChapterNotifyMessage;
}