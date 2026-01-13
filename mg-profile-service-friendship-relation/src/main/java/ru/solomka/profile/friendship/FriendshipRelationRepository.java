package ru.solomka.profile.friendship;

import ru.solomka.profile.common.EntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendshipRelationRepository extends EntityRepository<FriendshipRelationEntity> {

    Optional<FriendshipRelationEntity> findFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember);

    List<FriendshipRelationEntity> getFriendshipRelationsByRelationMemberId(UUID relationMemberId, RelationStatus status);

    List<FriendshipRelationEntity> getFriendshipRelationsByRelationInitiatorId(UUID relationInitiatorId, RelationStatus status);

    void deleteFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember);

    boolean existsByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember);
}