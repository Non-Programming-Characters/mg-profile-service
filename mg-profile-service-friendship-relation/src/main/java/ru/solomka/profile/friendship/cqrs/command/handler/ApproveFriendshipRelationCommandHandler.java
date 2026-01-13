package ru.solomka.profile.friendship.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.RelationStatus;
import ru.solomka.profile.friendship.cqrs.command.ApproveFriendshipRelationCommand;
import ru.solomka.profile.friendship.exception.RelationInvalidActionException;
import ru.solomka.profile.friendship.exception.RelationNotFoundException;
import ru.solomka.profile.profile.ProfileService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApproveFriendshipRelationCommandHandler implements CommandHandler<ApproveFriendshipRelationCommand, FriendshipRelationEntity> {

    @NonNull FriendshipRelationService friendshipRelationService;

    @NonNull ProfileService profileService;

    @Override
    public FriendshipRelationEntity handle(ApproveFriendshipRelationCommand command) {
        if(!profileService.existsById(command.getRelationInitiatorId()) || !profileService.existsById(command.getRelationMemberId()))
            throw new EntityNotFoundException("Specified friendship entity(-ies) not found");

        if(!friendshipRelationService.existsByRelationMembersId(command.getRelationInitiatorId(), command.getRelationMemberId()))
            throw new RelationNotFoundException("Friendship relation with specified entity(-ies) not found");

        FriendshipRelationEntity friendshipRelationEntity = friendshipRelationService.getFriendshipRelationByMembersId(
                command.getRelationInitiatorId(), command.getRelationMemberId()
        );

        if(command.getRelationMemberId().equals(friendshipRelationEntity.getInitiator()))
            throw new RelationInvalidActionException("Impossible to confirm friendship '%s' with these parameters of the initiator"
                    .formatted(command.getRelationMemberId()));

        friendshipRelationEntity.setStatus(RelationStatus.APPROVED);

        return friendshipRelationService.update(friendshipRelationEntity);
    }
}
