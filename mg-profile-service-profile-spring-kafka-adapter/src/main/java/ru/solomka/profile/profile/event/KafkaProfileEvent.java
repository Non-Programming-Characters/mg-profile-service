package ru.solomka.profile.profile.event;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class KafkaProfileEvent {

    @NonNull UUID id;

    @NonNull String userTag;

    @NonNull String firstName;

    String lastName;

    @NonNull String email;

    @NonNull Instant createdAt;
}