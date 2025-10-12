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
    UUID id;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "createdAt", nullable = false)
    Instant createdAt;
}