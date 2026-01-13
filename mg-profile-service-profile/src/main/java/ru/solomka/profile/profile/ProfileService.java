package ru.solomka.profile.profile;

import lombok.NonNull;
import ru.solomka.profile.common.EntityNotification;
import ru.solomka.profile.common.EntityService;
import ru.solomka.profile.common.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProfileService extends EntityService<ProfileEntity> {

    @NonNull ProfileRepository profileRepository;

    @NonNull EntityNotification<ProfileEntity> profileEntityEntityNotification;

    public ProfileService(@NonNull ProfileRepository profileRepository, @NonNull EntityNotification<ProfileEntity> profileEntityEntityNotification) {
        super(profileRepository);
        this.profileRepository = profileRepository;
        this.profileEntityEntityNotification = profileEntityEntityNotification;
    }

    @Override
    public ProfileEntity create(ProfileEntity entity) {
        ProfileEntity createdProfileEntity = super.create(entity);
        profileEntityEntityNotification.notifyCreate(createdProfileEntity);
        return createdProfileEntity;
    }

    @Override
    public ProfileEntity update(ProfileEntity entity) {
        ProfileEntity updatedProfileEntity = super.update(entity);
        profileEntityEntityNotification.notifyUpdate(updatedProfileEntity);
        return updatedProfileEntity;
    }

    @Override
    public ProfileEntity deleteById(UUID id) {
        ProfileEntity deletedProfileEntity = super.deleteById(id);
        profileEntityEntityNotification.notifyDelete(deletedProfileEntity);
        return deletedProfileEntity;
    }

    public ProfileEntity getProfileByUserTag(String userTag) {
        return this.profileRepository.findProfileByUserTag(userTag)
                .orElseThrow(() -> new EntityNotFoundException("Profile with user tag '%s' not found".formatted(userTag)));
    }

    public Optional<ProfileEntity> findProfileByUserTag(String userTag) {
        return this.profileRepository.findProfileByUserTag(userTag);
    }

    public List<ProfileEntity> getProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName) {
        return this.profileRepository.getProfilesByContainsFirstOrLastName(firstName, lastName);
    }

    public boolean existsByUserTag(String userTag) {
        return this.profileRepository.existsByUserTag(userTag);
    }
}