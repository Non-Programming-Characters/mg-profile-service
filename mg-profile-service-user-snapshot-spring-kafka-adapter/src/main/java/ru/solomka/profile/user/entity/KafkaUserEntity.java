package ru.solomka.profile.user.entity;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class KafkaUserEntity {

    @NonNull UUID id;

    @NonNull String login;

    @NonNull String email;

    @NonNull Instant createdAt;
}