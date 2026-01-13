package ru.solomka.profile.friendship.v1;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.cqrs.command.ApproveFriendshipRelationCommand;
import ru.solomka.profile.friendship.cqrs.command.CreateFriendshipRelationCommand;
import ru.solomka.profile.friendship.cqrs.command.TerminateFriendshipRelationCommand;
import ru.solomka.profile.friendship.request.FriendshipRelationPerformerRequest;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.friendship.response.FriendshipRelationPerformerResponse;

@RestController
@RequestMapping("/api/v1/profile/friends/relation")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendshipRelationLinkerRestController {

    @NonNull CommandHandler<CreateFriendshipRelationCommand, FriendshipRelationEntity> createFriendshipRelationCommandHandler;
    @NonNull CommandHandler<ApproveFriendshipRelationCommand, FriendshipRelationEntity> approveFriendShipRelationCommandHandler;
    @NonNull CommandHandler<TerminateFriendshipRelationCommand, FriendshipRelationEntity> terminateFriendshipRelationCommandHandler;

    @PostMapping(value = "/link", produces = "application/json")
    public ResponseEntity<FriendshipRelationPerformerResponse> linkFriendshipRelation(@RequestBody FriendshipRelationPerformerRequest friendshipRelationLinkRequest) {
        CreateFriendshipRelationCommand createFriendshipRelationCommand = new CreateFriendshipRelationCommand(
                friendshipRelationLinkRequest.getRelationInitiatorId(),
                friendshipRelationLinkRequest.getRelationMemberId()
        );
        FriendshipRelationEntity friendshipRelationEntity = createFriendshipRelationCommandHandler.handle(createFriendshipRelationCommand);
        return ResponseEntity.ok(new FriendshipRelationPerformerResponse(
                friendshipRelationEntity.getId(),
                friendshipRelationEntity.getMembers()
        ));
    }

    @PostMapping(value = "/unlink", produces = "application/json")
    public ResponseEntity<FriendshipRelationPerformerResponse> unlinkFriendshipRelation(@RequestBody FriendshipRelationPerformerRequest friendshipRelationUnlinkRequest) {
        TerminateFriendshipRelationCommand terminateFriendshipRelationCommand = new TerminateFriendshipRelationCommand(
                friendshipRelationUnlinkRequest.getRelationInitiatorId(),
                friendshipRelationUnlinkRequest.getRelationMemberId()
        );
        FriendshipRelationEntity friendshipRelationEntity = terminateFriendshipRelationCommandHandler.handle(terminateFriendshipRelationCommand);
        return ResponseEntity.ok(new FriendshipRelationPerformerResponse(friendshipRelationEntity.getId(), friendshipRelationEntity.getMembers()));
    }

    @PostMapping(value = "/approve", produces = "application/json")
    public ResponseEntity<FriendshipRelationPerformerResponse> approveFriendshipRelation(@RequestBody FriendshipRelationPerformerRequest friendshipRelationUnlinkRequest) {
        ApproveFriendshipRelationCommand approveFriendshipRelationCommand = new ApproveFriendshipRelationCommand(
                friendshipRelationUnlinkRequest.getRelationInitiatorId(),
                friendshipRelationUnlinkRequest.getRelationMemberId()
        );
        FriendshipRelationEntity friendshipRelationEntity = approveFriendShipRelationCommandHandler.handle(approveFriendshipRelationCommand);
        return ResponseEntity.ok(new FriendshipRelationPerformerResponse(friendshipRelationEntity.getId(), friendshipRelationEntity.getMembers()));
    }
}