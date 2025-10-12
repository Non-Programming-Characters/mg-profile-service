package ru.solomka.profile.profile;

import lombok.NonNull;
import ru.solomka.profile.common.BaseCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudProfileRepository extends BaseCrudRepository<JpaProfileEntity> {
    Optional<ProfileEntity> findProfileByProfileName(@NonNull String profileName);

    List<ProfileEntity> findProfilesByFirstName(@NonNull String firstName);
    List<ProfileEntity> findProfilesByLastName(@NonNull String lastName);
    List<ProfileEntity> findProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName);
}