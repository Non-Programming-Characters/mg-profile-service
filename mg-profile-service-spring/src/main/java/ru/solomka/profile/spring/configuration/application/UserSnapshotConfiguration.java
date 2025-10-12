package ru.solomka.profile.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.profile.common.mapper.Mapper;
import ru.solomka.profile.user.*;

@Configuration
@EntityScan(basePackageClasses = JpaUserSnapshotEntity.class)
@EnableJpaRepositories(basePackageClasses = CrudUserSnapshotRepository.class)
public class UserSnapshotConfiguration {

    @Bean
    UserSnapshotService userSnapshotService(@NonNull UserSnapshotRepository userSnapshotRepository) {
        return new UserSnapshotService(userSnapshotRepository);
    }

    @Bean
    CrudUserSnapshotServiceAdapter  crudUserSnapshotServiceAdapter(@NonNull CrudUserSnapshotRepository crudUserSnapshotRepository,
                                                                   @NonNull Mapper<JpaUserSnapshotEntity, UserSnapshotEntity> jpaUserSnapshotEntityUserSnapshotEntityMapper) {
        return new CrudUserSnapshotServiceAdapter(crudUserSnapshotRepository, jpaUserSnapshotEntityUserSnapshotEntityMapper);
    }

    @Bean
    JpaUserSnapshotUserSnapshotMapper jpaUserSnapshotUserSnapshotMapper() {
        return new JpaUserSnapshotUserSnapshotMapper();
    }
}