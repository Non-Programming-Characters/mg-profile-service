package ru.solomka.profile.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.profile.common.mapper.Mapper;
import ru.solomka.profile.profile.*;

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
}