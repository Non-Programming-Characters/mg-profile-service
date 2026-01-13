package ru.solomka.profile.profile;

import ru.solomka.profile.common.mapper.Mapper;

public class JpaProfileEntityProfileEntityMapper implements Mapper<JpaProfileEntity, ProfileEntity> {
    @Override
    public JpaProfileEntity mapToInfrastructure(ProfileEntity domainEntity) {
        return JpaProfileEntity.builder()
                .id(domainEntity.getId())
                .userTag(domainEntity.getUserTag())
                .firstName(domainEntity.getFirstName())
                .lastName(domainEntity.getLastName())
                .email(domainEntity.getEmail())
                .lastEditedAt(domainEntity.getLastEditedAt())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public ProfileEntity mapToDomain(JpaProfileEntity infrastructureEntity) {
        return ProfileEntity.builder()
                .id(infrastructureEntity.getId())
                .userTag(infrastructureEntity.getUserTag())
                .firstName(infrastructureEntity.getFirstName())
                .lastName(infrastructureEntity.getLastName())
                .email(infrastructureEntity.getEmail())
                .lastEditedAt(infrastructureEntity.getLastEditedAt())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}
