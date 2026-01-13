package ru.solomka.profile.friendship;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import ru.solomka.profile.common.BaseJpaRepositoryAdapter;
import ru.solomka.profile.common.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrudFriendshipRelationRepositoryAdapter extends BaseJpaRepositoryAdapter<JpaFriendshipRelationEntity, FriendshipRelationEntity> implements FriendshipRelationRepository {

    @NonNull CrudFriendshipRelationRepository crudFriendshipRelationRepository;
    @NonNull Mapper<JpaFriendshipRelationEntity, FriendshipRelationEntity> mapper;

    public CrudFriendshipRelationRepositoryAdapter(@NonNull CrudFriendshipRelationRepository repository,
                                                   @NonNull Mapper<JpaFriendshipRelationEntity, FriendshipRelationEntity> mapper) {
        super(repository, mapper);
        this.crudFriendshipRelationRepository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<FriendshipRelationEntity> findFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        return this.crudFriendshipRelationRepository.getFriendshipRelationByRelationMembersId(relationFirstMember, relationSecondMember)
                .map(mapper::mapToDomain);
    }

    @Override
    public List<FriendshipRelationEntity> getFriendshipRelationsByRelationMemberId(UUID relationMemberId, RelationStatus status) {
        return this.crudFriendshipRelationRepository.getRelationsByRelationMemberId(relationMemberId, status).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public List<FriendshipRelationEntity> getFriendshipRelationsByRelationInitiatorId(UUID relationInitiatorId, RelationStatus status) {
        return this.crudFriendshipRelationRepository.getRelationsByRelationInitiatorId(relationInitiatorId, status).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        this.crudFriendshipRelationRepository.deleteRelationByRelationMembersId(relationFirstMember, relationSecondMember);
    }

    @Override
    public boolean existsByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        return this.crudFriendshipRelationRepository.existsByRelationMembersId(relationFirstMember, relationSecondMember);
    }
}