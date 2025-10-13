package ru.solomka.profile.profile;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.BaseJpaRepositoryAdapter;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.common.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CrudProfileServiceAdapter extends BaseJpaRepositoryAdapter<JpaProfileEntity, ProfileEntity> implements ProfileRepository {

    @NonNull CrudProfileRepository crudProfileRepository;
    @NonNull Mapper<JpaProfileEntity, ProfileEntity>  jpaProfileEntityProfileEntityMapper;

    public CrudProfileServiceAdapter(@NonNull CrudProfileRepository repository,
                                     @NonNull Mapper<JpaProfileEntity, ProfileEntity> mapper) {
        super(repository, mapper);
        this.crudProfileRepository = repository;
        this.jpaProfileEntityProfileEntityMapper = mapper;
    }

    @Override
    public ProfileEntity getProfileByProfileName(@NonNull String profileName) {
        return crudProfileRepository.findProfileByProfileName(profileName)
                .map(jpaProfileEntityProfileEntityMapper::mapToDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Profile with profile name '%s' not found", profileName)));
    }

    @Override
    public List<ProfileEntity> getProfilesByFirstName(@NonNull String firstName) {
        return crudProfileRepository.findProfilesByFirstName(firstName).stream()
                .map(jpaProfileEntityProfileEntityMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileEntity> getProfilesByLastName(@NonNull String lastName) {
        return crudProfileRepository.findProfilesByLastName(lastName).stream()
                .map(jpaProfileEntityProfileEntityMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileEntity> getProfilesByFirstNameAndLastName(@NonNull String firstName, @NonNull String lastName) {
        return crudProfileRepository.findProfilesByFirstNameAndLastName(firstName, lastName).stream()
                .map(jpaProfileEntityProfileEntityMapper::mapToDomain)
                .collect(Collectors.toList());
    }
}