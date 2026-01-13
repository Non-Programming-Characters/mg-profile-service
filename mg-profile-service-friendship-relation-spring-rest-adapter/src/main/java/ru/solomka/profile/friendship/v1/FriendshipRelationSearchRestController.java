package ru.solomka.profile.friendship.v1;


import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.RelationStatus;
import ru.solomka.profile.friendship.cqrs.query.GetFriendshipRelationByRelationMembersIdQuery;
import ru.solomka.profile.friendship.cqrs.query.GetFriendshipRelationsByRelationInitiatorIdQuery;
import ru.solomka.profile.friendship.cqrs.query.GetFriendshipRelationsByRelationMemberIdQuery;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.common.exception.InvalidRequestException;
import ru.solomka.profile.friendship.response.FriendshipRelationSearchResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile/friends")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendshipRelationSearchRestController {

    @NonNull CommandHandler<GetFriendshipRelationsByRelationMemberIdQuery, List<FriendshipRelationEntity>> friendshipRelationsByRelationMemberIdQueryHandler;
    @NonNull CommandHandler<GetFriendshipRelationsByRelationInitiatorIdQuery, List<FriendshipRelationEntity>> friendshipRelationsByRelationInitiatorIdQueryHandler;
    @NonNull CommandHandler<GetFriendshipRelationByRelationMembersIdQuery, FriendshipRelationEntity> friendshipRelationByRelationMembersIdQueryHandler;

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<FriendshipRelationSearchResponse> searchFriendshipBetweenMembers(@RequestParam(value = "relationFirstMemberId") UUID relationFirstMemberId,
                                                                                           @RequestParam(value = "relationSecondMemberId") UUID relationSecondMemberId) throws InvalidRequestException {
        GetFriendshipRelationByRelationMembersIdQuery getFriendshipRelationByRelationMembersIdQuery = new GetFriendshipRelationByRelationMembersIdQuery(
                relationFirstMemberId, relationSecondMemberId
        );
        FriendshipRelationEntity friendshipRelationEntity = friendshipRelationByRelationMembersIdQueryHandler.handle(getFriendshipRelationByRelationMembersIdQuery);
        return ResponseEntity.ok(new FriendshipRelationSearchResponse(
                friendshipRelationEntity.getId(), friendshipRelationEntity.getMembers(),
                friendshipRelationEntity.getStatus(), friendshipRelationEntity.getCreatedAt()
        ));
    }

    @GetMapping(value = "/{userId}/search", produces = "application/json")
    public ResponseEntity<List<FriendshipRelationSearchResponse>> localFriendshipRelationSearch(@PathVariable(value = "userId") UUID userId,
                                                                                                @RequestParam(value = "type") String type,
                                                                                                @RequestParam(value = "status") String status) {
        switch (type.toLowerCase()) {
            case "initiator" -> {
                GetFriendshipRelationsByRelationInitiatorIdQuery getFriendshipRelationsByRelationInitiatorIdQuery = new GetFriendshipRelationsByRelationInitiatorIdQuery(
                        userId, RelationStatus.valueOf(status.toUpperCase())
                );
                List<FriendshipRelationEntity> friendshipRelationEntities = friendshipRelationsByRelationInitiatorIdQueryHandler.handle(
                        getFriendshipRelationsByRelationInitiatorIdQuery
                );
                return ResponseEntity.ok(friendshipRelationEntities.stream()
                        .map(e -> new FriendshipRelationSearchResponse(e.getId(), e.getMembers(), e.getStatus(), e.getCreatedAt()))
                        .toList());
            }
            case "member" -> {
                GetFriendshipRelationsByRelationMemberIdQuery getFriendshipRelationsByRelationMemberIdQueryHandler = new GetFriendshipRelationsByRelationMemberIdQuery(
                        userId, RelationStatus.valueOf(status.toUpperCase())
                );
                List<FriendshipRelationEntity> friendshipRelationEntities = friendshipRelationsByRelationMemberIdQueryHandler.handle(getFriendshipRelationsByRelationMemberIdQueryHandler);
                return ResponseEntity.ok(friendshipRelationEntities.stream()
                        .map(e -> new FriendshipRelationSearchResponse(e.getId(), e.getMembers(), e.getStatus(), e.getCreatedAt()))
                        .toList()
                );
            }
            default -> throw new IllegalArgumentException("Invalid search type parameter");
        }
    }
}