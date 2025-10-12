package ru.solomka.profile.profile;

import ru.solomka.profile.common.mapper.Mapper;

public class JpaProfileEntityProfileEntityMapper implements Mapper<JpaProfileEntity, ProfileEntity> {
    @Override
    public JpaProfileEntity mapToInfrastructure(ProfileEntity domainEntity) {
        return JpaProfileEntity.builder()
                .id(domainEntity.getId())
                .profileName(domainEntity.getProfileName())
                .firstName(domainEntity.getFirstName())
                .lastName(domainEntity.getLastName())
                .bio(domainEntity.getBio())
                .birthDate(domainEntity.getBirthDate())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }

    @Override
    public ProfileEntity mapToDomain(JpaProfileEntity infrastructureEntity) {
        return ProfileEntity.builder()
                .id(infrastructureEntity.getId())
                .profileName(infrastructureEntity.getProfileName())
                .firstName(infrastructureEntity.getFirstName())
                .lastName(infrastructureEntity.getLastName())
                .bio(infrastructureEntity.getBio())
                .birthDate(infrastructureEntity.getBirthDate())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }
}
