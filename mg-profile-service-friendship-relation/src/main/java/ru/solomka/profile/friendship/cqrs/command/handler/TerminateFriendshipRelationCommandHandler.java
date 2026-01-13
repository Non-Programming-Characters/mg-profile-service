package ru.solomka.profile.friendship.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.cqrs.command.TerminateFriendshipRelationCommand;
import ru.solomka.profile.friendship.exception.RelationNotFoundException;
import ru.solomka.profile.common.cqrs.CommandHandler;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TerminateFriendshipRelationCommandHandler implements CommandHandler<TerminateFriendshipRelationCommand, FriendshipRelationEntity> {

    @NonNull FriendshipRelationService friendshipRelationService;

    @Override
    public FriendshipRelationEntity handle(TerminateFriendshipRelationCommand command) {
        if(!friendshipRelationService.existsByRelationMembersId(command.getRelationMemberInitiatorId(), command.getRelationMemberTargetId()))
            throw new RelationNotFoundException("Friendship relation between '%s' and '%s' not found"
                    .formatted(command.getRelationMemberInitiatorId(), command.getRelationMemberTargetId()));

        return friendshipRelationService.deleteFriendshipRelationByRelationMembersId(
                command.getRelationMemberInitiatorId(),
                command.getRelationMemberTargetId()
        );
    }
}