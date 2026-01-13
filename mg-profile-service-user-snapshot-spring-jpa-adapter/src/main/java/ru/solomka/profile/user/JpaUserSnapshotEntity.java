package ru.solomka.profile.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "profile_user_snapshots")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class JpaUserSnapshotEntity {

    @Id
    @NonNull UUID id;

    @Column(name = "login", nullable = false)
    @NonNull String login;

    @Column(name = "email", nullable = false)
    @NonNull String email;

    @Column(name = "created_at", nullable = false)
    @NonNull Instant createdAt;
}