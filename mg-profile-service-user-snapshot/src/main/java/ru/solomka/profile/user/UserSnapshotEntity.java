package ru.solomka.profile.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class UserSnapshotEntity implements Entity {

    @NonNull UUID id;

    @NonNull String email;

    @NonNull Instant createdAt;
}