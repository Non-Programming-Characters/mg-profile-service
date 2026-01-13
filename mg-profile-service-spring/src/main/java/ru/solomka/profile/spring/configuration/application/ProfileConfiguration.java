package ru.solomka.profile.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.profile.common.EntityNotification;
import ru.solomka.profile.common.mapper.Mapper;
import ru.solomka.profile.profile.*;
import ru.solomka.profile.profile.cqrs.command.handler.EditProfileInformationCommandHandler;
import ru.solomka.profile.profile.cqrs.query.handler.*;
import ru.solomka.profile.spring.configuration.properties.ProfilePropertiesConfiguration;

@Configuration
@EntityScan(basePackageClasses = JpaProfileEntity.class)
@EnableJpaRepositories(basePackageClasses = CrudProfileRepository.class)
public class ProfileConfiguration {

    @Bean
    ProfileService profileService(@NonNull ProfileRepository profileRepository,
                                  @NonNull EntityNotification<ProfileEntity> profileEntityNotification) {
        return new ProfileService(profileRepository, profileEntityNotification);
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
    EditProfileInformationCommandHandler editProfileInformationCommandHandler(@NonNull ProfileService profileService,
                                                                              @NonNull ProfilePropertiesConfiguration profilePropertiesConfiguration) {
        return new EditProfileInformationCommandHandler(profileService, profilePropertiesConfiguration.getProfileProperties().getProfileEditCooldown());
    }

    @Bean
    GetProfileByIdQueryHandler getProfileByIdQueryHandler(@NonNull ProfileService profileService) {
        return new GetProfileByIdQueryHandler(profileService);
    }

    @Bean
    GetProfileByUserTagQueryHandler getProfileByUserTagQueryHandler(@NonNull ProfileService profileService) {
        return new GetProfileByUserTagQueryHandler(profileService);
    }

    @Bean
    GetAllProfilesByContainsFirstOrLastNameQueryHandler getAllProfilesByContainsFirstOrLastNameQueryHandler(@NonNull ProfileService profileService) {
        return new GetAllProfilesByContainsFirstOrLastNameQueryHandler(profileService);
    }
}