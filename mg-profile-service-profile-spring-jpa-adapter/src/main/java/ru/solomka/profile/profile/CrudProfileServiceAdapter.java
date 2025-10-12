package ru.solomka.profile.profile;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.BaseCrudRepository;
import ru.solomka.profile.common.BaseJpaRepositoryAdapter;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.common.mapper.Mapper;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CrudProfileServiceAdapter extends BaseJpaRepositoryAdapter<JpaProfileEntity, ProfileEntity> implements ProfileRepository {

    @NonNull CrudProfileRepository crudProfileRepository;


    public CrudProfileServiceAdapter(@NonNull CrudProfileRepository repository,
                                     @NonNull Mapper<JpaProfileEntity, ProfileEntity> mapper) {
        super(repository, mapper);
        this.crudProfileRepository = repository;
    }

    @Override
    public ProfileEntity getProfileByProfileName(@NonNull String profileName) {
        return crudProfileRepository.findProfileByProfileName(profileName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Profile with profile name '%s' not found", profileName)));
    }

    @Override
    public List<ProfileEntity> getProfilesByFirstName(@NonNull String firstName) {
        return crudProfileRepository.findProfilesByFirstName(firstName);
    }

    @Override
    public List<ProfileEntity> getProfilesByLastName(@NonNull String lastName) {
        return crudProfileRepository.findProfilesByLastName(lastName);
    }

    @Override
    public List<ProfileEntity> getProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName) {
        return crudProfileRepository.findProfilesByFirstNameAndLastName(firstName, lastName);
    }
}