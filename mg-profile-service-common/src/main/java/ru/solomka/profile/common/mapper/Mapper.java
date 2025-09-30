package ru.solomka.profile.common.mapper;

public interface Mapper<I, D> {
    I mapToInfrastructure(D domainEntity);

    D mapToDomain(I infrastructureEntity);
}