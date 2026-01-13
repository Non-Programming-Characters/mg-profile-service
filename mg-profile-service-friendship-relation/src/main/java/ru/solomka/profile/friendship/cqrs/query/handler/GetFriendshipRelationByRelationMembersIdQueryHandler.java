package ru.solomka.profile.friendship.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.cqrs.query.GetFriendshipRelationByRelationMembersIdQuery;
import ru.solomka.profile.common.cqrs.CommandHandler;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetFriendshipRelationByRelationMembersIdQueryHandler implements CommandHandler<GetFriendshipRelationByRelationMembersIdQuery, FriendshipRelationEntity> {

    @NonNull FriendshipRelationService friendshipRelationService;

    @Override
    public FriendshipRelationEntity handle(GetFriendshipRelationByRelationMembersIdQuery command) {
        return this.friendshipRelationService.getFriendshipRelationByMembersId(command.getRelationFirstMemberId(), command.getRelationSecondMemberId());
    }
}