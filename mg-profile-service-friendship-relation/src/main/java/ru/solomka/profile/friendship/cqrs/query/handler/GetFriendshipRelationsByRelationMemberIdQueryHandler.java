package ru.solomka.profile.friendship.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.cqrs.query.GetFriendshipRelationsByRelationMemberIdQuery;
import ru.solomka.profile.common.cqrs.CommandHandler;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetFriendshipRelationsByRelationMemberIdQueryHandler implements CommandHandler<GetFriendshipRelationsByRelationMemberIdQuery, List<FriendshipRelationEntity>> {

    @NonNull FriendshipRelationService friendshipRelationService;

    @Override
    public List<FriendshipRelationEntity> handle(GetFriendshipRelationsByRelationMemberIdQuery command) {
        return this.friendshipRelationService.getFriendshipRelationsByRelationMemberId(command.getRelationMemberId(), command.getStatus());
    }
}