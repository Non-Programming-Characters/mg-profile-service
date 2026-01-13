package ru.solomka.profile.friendship;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.Entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendshipRelationEntity implements Entity {

    UUID id;

    @NonNull UUID initiator;

    @NonNull List<UUID> members;

    @NonNull RelationStatus status;

    Instant createdAt;
}