package ru.solomka.profile.friendship;

import lombok.NonNull;
import ru.solomka.profile.friendship.exception.RelationNotFoundException;
import ru.solomka.profile.common.EntityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FriendshipRelationService extends EntityService<FriendshipRelationEntity> {

    @NonNull FriendshipRelationRepository friendshipRelationRepository;

    public FriendshipRelationService(@NonNull FriendshipRelationRepository repository) {
        super(repository);
        this.friendshipRelationRepository = repository;
    }

    public FriendshipRelationEntity getFriendshipRelationByMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        return this.friendshipRelationRepository.findFriendshipRelationByRelationMembersId(relationFirstMember, relationSecondMember)
                .orElseThrow(() -> new RelationNotFoundException("Friendship relation between '%s' and '%s' not found"
                        .formatted(relationFirstMember, relationSecondMember)));
    }

    public Optional<FriendshipRelationEntity> findFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        return this.friendshipRelationRepository.findFriendshipRelationByRelationMembersId(relationFirstMember, relationSecondMember);
    }

    public List<FriendshipRelationEntity> getFriendshipRelationsByRelationMemberId(UUID relationMemberId, RelationStatus status) {
        return this.friendshipRelationRepository.getFriendshipRelationsByRelationMemberId(relationMemberId, status);
    }

    public List<FriendshipRelationEntity> getFriendshipRelationsByRelationInitiatorId(UUID relationInitiatorId, RelationStatus status) {
        return this.friendshipRelationRepository.getFriendshipRelationsByRelationInitiatorId(relationInitiatorId, status);
    }

    public FriendshipRelationEntity deleteFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        FriendshipRelationEntity friendshipRelationEntity = this.getFriendshipRelationByMembersId(relationFirstMember, relationSecondMember);
        this.friendshipRelationRepository.deleteFriendshipRelationByRelationMembersId(relationFirstMember, relationSecondMember);
        return friendshipRelationEntity;
    }

    public boolean existsByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember) {
        return this.friendshipRelationRepository.existsByRelationMembersId(relationFirstMember, relationSecondMember);
    }
}