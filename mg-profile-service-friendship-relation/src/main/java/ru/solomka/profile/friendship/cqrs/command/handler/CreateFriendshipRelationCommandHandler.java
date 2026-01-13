package ru.solomka.profile.friendship.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.RelationStatus;
import ru.solomka.profile.friendship.cqrs.command.CreateFriendshipRelationCommand;
import ru.solomka.profile.friendship.exception.RelationAlreadyExists;
import ru.solomka.profile.common.cqrs.CommandHandler;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateFriendshipRelationCommandHandler implements CommandHandler<CreateFriendshipRelationCommand, FriendshipRelationEntity> {

    @NonNull FriendshipRelationService friendshipRelationService;

    @Override
    public FriendshipRelationEntity handle(CreateFriendshipRelationCommand command) {

        if(friendshipRelationService.existsByRelationMembersId(command.getRelationInitiatorId(), command.getRelationMemberId()))
            throw new RelationAlreadyExists("Friendship between '%s' and '%s' already exists"
                    .formatted(command.getRelationInitiatorId(), command.getRelationMemberId()));

        FriendshipRelationEntity friendshipRelationEntity = FriendshipRelationEntity.builder()
                .initiator(command.getRelationInitiatorId())
                .members(List.of(command.getRelationInitiatorId(), command.getRelationMemberId()))
                .status(RelationStatus.NOT_APPROVED)
                .build();

        return friendshipRelationService.create(friendshipRelationEntity);
    }
}