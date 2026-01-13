package ru.solomka.profile.profile;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JpaProfileEntity {

    @Id
    @Column(name = "id", nullable = false)
    @NonNull UUID id;

    @Column(name = "user_tag", length = 30, nullable = false)
    @NonNull String userTag;

    @Column(name = "first_name", nullable = false)
    @NonNull String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email")
    @NonNull String email;

    @Column(name = "last_edited_at", nullable = false)
    @NonNull Instant lastEditedAt;

    @Column(name = "created_at")
    @NonNull Instant createdAt;
}