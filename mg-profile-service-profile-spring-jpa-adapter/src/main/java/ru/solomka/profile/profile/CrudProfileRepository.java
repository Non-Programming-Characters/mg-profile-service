package ru.solomka.profile.profile;

import lombok.NonNull;
import ru.solomka.profile.common.BaseCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudProfileRepository extends BaseCrudRepository<JpaProfileEntity> {
    Optional<JpaProfileEntity> findProfileByProfileName(@NonNull String profileName);

    List<JpaProfileEntity> findProfilesByFirstName(@NonNull String firstName);
    List<JpaProfileEntity> findProfilesByLastName(@NonNull String lastName);
    List<JpaProfileEntity> findProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName);
}