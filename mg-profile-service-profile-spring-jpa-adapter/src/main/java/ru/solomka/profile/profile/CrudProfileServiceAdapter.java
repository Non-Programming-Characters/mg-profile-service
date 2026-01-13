package ru.solomka.profile.profile;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.BaseJpaRepositoryAdapter;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.common.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CrudProfileServiceAdapter extends BaseJpaRepositoryAdapter<JpaProfileEntity, ProfileEntity> implements ProfileRepository {

    @NonNull CrudProfileRepository crudProfileRepository;
    @NonNull Mapper<JpaProfileEntity, ProfileEntity>  mapper;

    public CrudProfileServiceAdapter(@NonNull CrudProfileRepository repository,
                                     @NonNull Mapper<JpaProfileEntity, ProfileEntity> mapper) {
        super(repository, mapper);
        this.crudProfileRepository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ProfileEntity> findProfileByUserTag(String userTag) {
        return this.crudProfileRepository.findProfileByUserTag(userTag).map(mapper::mapToDomain);
    }

    @Override
    public List<ProfileEntity> getProfilesByContainsFirstOrLastName(@NonNull String firstName, String lastName) {
        return this.crudProfileRepository.getProfilesByContainsFirstOrLastName(firstName, lastName).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public boolean existsByUserTag(String userTag) {
        return this.crudProfileRepository.existsByUserTag(userTag);
    }
}