package ru.solomka.profile.profile;

import lombok.NonNull;
import ru.solomka.profile.common.EntityRepository;

import java.util.List;

public interface ProfileRepository extends EntityRepository<ProfileEntity> {
    ProfileEntity getProfileByProfileName(@NonNull String profileName);

    List<ProfileEntity> getProfilesByFirstName(@NonNull String firstName);
    List<ProfileEntity> getProfilesByLastName(@NonNull String lastName);
    List<ProfileEntity> getProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName);
}