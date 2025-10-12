package ru.solomka.profile.user;

import ru.solomka.profile.common.mapper.Mapper;

public class JpaUserSnapshotUserSnapshotMapper implements Mapper<JpaUserSnapshotEntity, UserSnapshotEntity> {

    @Override
    public JpaUserSnapshotEntity mapToInfrastructure(UserSnapshotEntity domainEntity) {
        return JpaUserSnapshotEntity.builder()
                .id(domainEntity.getId())
                .email(domainEntity.getEmail())
                .build();
    }

    @Override
    public UserSnapshotEntity mapToDomain(JpaUserSnapshotEntity infrastructureEntity) {
        return UserSnapshotEntity.builder()
                .id(infrastructureEntity.getId())
                .email(infrastructureEntity.getEmail())
                .build();
    }
}