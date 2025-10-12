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
    @NonNull UUID id;

    @Column(name = "profile_name")
    String profileName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "bio")
    String bio;

    @Column(name = "birthdate")
    Instant birthDate;

    @Column(name = "created_at")
    @NonNull Instant createdAt;

}
