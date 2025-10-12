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

    @NonNull UUID id;

    String profileName;

    String firstName;

    String lastName;

    String bio;

    Instant birthDate;

    @NonNull Instant createdAt;
}