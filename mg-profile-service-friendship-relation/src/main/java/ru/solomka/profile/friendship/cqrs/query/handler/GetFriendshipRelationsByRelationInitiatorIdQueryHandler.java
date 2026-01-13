package ru.solomka.profile.friendship.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.cqrs.query.GetFriendshipRelationsByRelationInitiatorIdQuery;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetFriendshipRelationsByRelationInitiatorIdQueryHandler implements CommandHandler<GetFriendshipRelationsByRelationInitiatorIdQuery, List<FriendshipRelationEntity>> {

    @NonNull FriendshipRelationService friendshipRelationService;

    @Override
    public List<FriendshipRelationEntity> handle(GetFriendshipRelationsByRelationInitiatorIdQuery command) {
        return friendshipRelationService.getFriendshipRelationsByRelationInitiatorId(command.getInitiatorId(), command.getStatus());
    }
}
