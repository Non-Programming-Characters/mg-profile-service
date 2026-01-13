package ru.solomka.profile.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.profile.common.mapper.Mapper;
import ru.solomka.profile.friendship.*;
import ru.solomka.profile.friendship.cqrs.command.handler.ApproveFriendshipRelationCommandHandler;
import ru.solomka.profile.friendship.cqrs.command.handler.CreateFriendshipRelationCommandHandler;
import ru.solomka.profile.friendship.cqrs.command.handler.TerminateFriendshipRelationCommandHandler;
import ru.solomka.profile.friendship.cqrs.query.handler.GetFriendshipRelationByRelationMembersIdQueryHandler;
import ru.solomka.profile.friendship.cqrs.query.handler.GetFriendshipRelationsByRelationInitiatorIdQueryHandler;
import ru.solomka.profile.friendship.cqrs.query.handler.GetFriendshipRelationsByRelationMemberIdQueryHandler;
import ru.solomka.profile.profile.ProfileService;

@Configuration
@EnableJpaRepositories(basePackageClasses = CrudFriendshipRelationRepository.class)
@EntityScan(basePackageClasses = JpaFriendshipRelationEntity.class)
public class FriendshipRelationConfiguration {

    @Bean
    FriendshipRelationService friendshipRelationService(@NonNull FriendshipRelationRepository friendshipRelationRepository) {
        return new FriendshipRelationService(friendshipRelationRepository);
    }

    @Bean
    CrudFriendshipRelationRepositoryAdapter crudFriendshipRelationRepositoryAdapter(@NonNull CrudFriendshipRelationRepository crudFriendshipRelationRepository,
                                                                                    @NonNull Mapper<JpaFriendshipRelationEntity, FriendshipRelationEntity> mapper) {
        return new CrudFriendshipRelationRepositoryAdapter(crudFriendshipRelationRepository, mapper);
    }

    @Bean
    JpaFriendshipRelationFriendshipRelationMapper jpaFriendshipRelationFriendshipRelationMapper() {
        return new JpaFriendshipRelationFriendshipRelationMapper();
    }

    @Bean
    ApproveFriendshipRelationCommandHandler approveFriendshipRelationCommandHandler(@NonNull FriendshipRelationService friendshipRelationService,
                                                                                    @NonNull ProfileService profileService) {
        return new ApproveFriendshipRelationCommandHandler(friendshipRelationService, profileService);
    }

    @Bean
    CreateFriendshipRelationCommandHandler createFriendshipRelationCommandHandler(@NonNull FriendshipRelationService friendshipRelationService) {
        return new CreateFriendshipRelationCommandHandler(friendshipRelationService);
    }

    @Bean
    TerminateFriendshipRelationCommandHandler terminateFriendshipRelationCommandHandler(@NonNull FriendshipRelationService friendshipRelationService) {
        return new TerminateFriendshipRelationCommandHandler(friendshipRelationService);
    }

    @Bean
    GetFriendshipRelationByRelationMembersIdQueryHandler getFriendshipRelationByRelationMembersIdQueryHandler(@NonNull FriendshipRelationService friendshipRelationService) {
        return new GetFriendshipRelationByRelationMembersIdQueryHandler(friendshipRelationService);
    }

    @Bean
    GetFriendshipRelationsByRelationMemberIdQueryHandler getFriendshipRelationsByRelationMemberIdQueryHandler(@NonNull FriendshipRelationService friendshipRelationService) {
        return new GetFriendshipRelationsByRelationMemberIdQueryHandler(friendshipRelationService);
    }
    @Bean
    GetFriendshipRelationsByRelationInitiatorIdQueryHandler getFriendshipRelationsByRelationInitiatorIdQueryHandler(@NonNull FriendshipRelationService friendshipRelationService) {
        return new GetFriendshipRelationsByRelationInitiatorIdQueryHandler(friendshipRelationService);
    }
}
