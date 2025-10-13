package ru.solomka.profile.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.profile.common.mapper.Mapper;
import ru.solomka.profile.profile.*;
import ru.solomka.profile.profile.cqrs.query.handler.*;

@Configuration
@EntityScan(basePackageClasses = JpaProfileEntity.class)
@EnableJpaRepositories(basePackageClasses = CrudProfileRepository.class)
public class ProfileConfiguration {

    @Bean
    ProfileService profileService(@NonNull ProfileRepository profileRepository) {
        return new ProfileService(profileRepository);
    }

    @Bean
    CrudProfileServiceAdapter crudProfileServiceAdapter(@NonNull CrudProfileRepository crudProfileRepository,
                                                        @NonNull Mapper<JpaProfileEntity, ProfileEntity> jpaProfileEntityProfileEntityMapper) {
        return new CrudProfileServiceAdapter(crudProfileRepository, jpaProfileEntityProfileEntityMapper);
    }

    @Bean
    JpaProfileEntityProfileEntityMapper jpaProfileEntityProfileEntityMapper() {
        return new JpaProfileEntityProfileEntityMapper();
    }

    @Bean
    GetProfilesByFirstNameAndLastNameQueryHandler getProfilesByFirstNameAndLastNameQuery(@NonNull ProfileService profileService) {
        return new GetProfilesByFirstNameAndLastNameQueryHandler(profileService);
    }

    @Bean
    GetProfilesByFirstNameQueryHandler getProfilesByFirstNameQueryHandler(@NonNull ProfileService profileService) {
        return new GetProfilesByFirstNameQueryHandler(profileService);
    }

    @Bean
    GetProfilesByLastNameQueryHandler getProfilesByLastNameQueryHandler(@NonNull ProfileService profileService) {
        return new GetProfilesByLastNameQueryHandler(profileService);
    }

    @Bean
    GetProfileByProfileNameQueryHandler getProfileByProfileNameQueryHandler(@NonNull ProfileService profileService) {
        return new GetProfileByProfileNameQueryHandler(profileService);
    }

    @Bean
    GetProfileByIdQueryHandler getProfileByIdQueryHandler(@NonNull ProfileService profileService) {
        return new GetProfileByIdQueryHandler(profileService);
    }
}