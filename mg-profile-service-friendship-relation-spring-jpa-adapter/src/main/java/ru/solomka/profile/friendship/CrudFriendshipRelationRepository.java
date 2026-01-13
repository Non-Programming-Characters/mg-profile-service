package ru.solomka.profile.friendship;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.solomka.profile.common.BaseCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudFriendshipRelationRepository extends BaseCrudRepository<JpaFriendshipRelationEntity> {

    @Query(
            "select jfre from JpaFriendshipRelationEntity jfre where " +
            ":relationFirstMember member of jfre.members and :relationSecondMember member of jfre.members"
    )
    Optional<JpaFriendshipRelationEntity> getFriendshipRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember);

    @Query("select jfre from JpaFriendshipRelationEntity jfre where :relationMemberId member of jfre.members and jfre.initiator <> :relationMemberId and jfre.status = :status")
    List<JpaFriendshipRelationEntity> getRelationsByRelationMemberId(UUID relationMemberId, RelationStatus status);

    @Query("select jfre from JpaFriendshipRelationEntity jfre where :relationInitiatorId member of jfre.members and jfre.initiator = :relationInitiatorId and jfre.status = :status")
    List<JpaFriendshipRelationEntity> getRelationsByRelationInitiatorId(UUID relationInitiatorId, RelationStatus status);

    @Query(
            "select count(jfre) > 0 from JpaFriendshipRelationEntity jfre where " +
            ":relationFirstMember member of jfre.members and :relationSecondMember member of jfre.members"
    )
    boolean existsByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember);

    @Modifying
    @Query(
            "delete from JpaFriendshipRelationEntity jfre where " +
            ":relationFirstMember member of jfre.members and :relationSecondMember member of jfre.members"
    )
    void deleteRelationByRelationMembersId(UUID relationFirstMember, UUID relationSecondMember);
}