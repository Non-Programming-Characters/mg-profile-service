package ru.solomka.profile.profile;

import lombok.NonNull;
import ru.solomka.profile.common.EntityService;

import java.util.List;

public class ProfileService extends EntityService<ProfileEntity> {

    @NonNull ProfileRepository profileRepository;

    public ProfileService(@NonNull ProfileRepository profileRepository) {
        super(profileRepository);
        this.profileRepository = profileRepository;
    }

    public ProfileEntity getProfileByProfileName(@NonNull String profileName) {
        return this.profileRepository.getProfileByProfileName(profileName);
    }

    public List<ProfileEntity> getProfilesByFirstName(@NonNull String firstName) {
        return this.profileRepository.getProfilesByFirstName(firstName);
    }
    public List<ProfileEntity> getProfilesByLastName(@NonNull String lastName) {
        return this.profileRepository.getProfilesByLastName(lastName);
    }
    public List<ProfileEntity> getProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName) {
        return this.profileRepository.getProfilesByFirstNameAndLastName(firstName, lastName);
    }
}