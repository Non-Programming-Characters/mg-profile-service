package ru.solomka.profile.friendship;

import ru.solomka.profile.common.mapper.Mapper;

public class JpaFriendshipRelationFriendshipRelationMapper implements Mapper<JpaFriendshipRelationEntity, FriendshipRelationEntity> {

    @Override
    public JpaFriendshipRelationEntity mapToInfrastructure(FriendshipRelationEntity domainEntity) {
        return JpaFriendshipRelationEntity.builder()
                .id(domainEntity.getId())
                .initiator(domainEntity.getInitiator())
                .members(domainEntity.getMembers())
                .status(domainEntity.getStatus())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public FriendshipRelationEntity mapToDomain(JpaFriendshipRelationEntity infrastructureEntity) {
        return FriendshipRelationEntity.builder()
                .id(infrastructureEntity.getId())
                .initiator(infrastructureEntity.getInitiator())
                .members(infrastructureEntity.getMembers())
                .status(infrastructureEntity.getStatus())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}