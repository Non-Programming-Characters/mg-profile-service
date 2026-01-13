package ru.solomka.profile.profile;

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
public class ProfileEntity implements Entity {

    UUID id;

    @NonNull String userTag;

    @NonNull String firstName;

    String lastName;

    @NonNull String email;

    @NonNull Instant lastEditedAt;

    Instant createdAt;
}