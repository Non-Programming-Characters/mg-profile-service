package ru.solomka.profile.friendship.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Friendship Relations", description = "API for managing friendship connections between users")
public class FriendshipRelationLinkerRestController {

    @NonNull CommandHandler<CreateFriendshipRelationCommand, FriendshipRelationEntity> createFriendshipRelationCommandHandler;
    @NonNull CommandHandler<ApproveFriendshipRelationCommand, FriendshipRelationEntity> approveFriendShipRelationCommandHandler;
    @NonNull CommandHandler<TerminateFriendshipRelationCommand, FriendshipRelationEntity> terminateFriendshipRelationCommandHandler;

    @PostMapping(value = "/link", produces = "application/json")
    @Operation(
            summary = "Send a friend request",
            description = "Initiates a new friendship relation by sending a request from one user to another."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Friend request sent successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FriendshipRelationPerformerResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid or missing user IDs", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict: Friendship relation already exists or is in incompatible state", content = @Content)
    public ResponseEntity<FriendshipRelationPerformerResponse> linkFriendshipRelation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "IDs of the initiator and target user",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FriendshipRelationPerformerRequest.class))
            )
            @RequestBody FriendshipRelationPerformerRequest friendshipRelationLinkRequest
    ) {
        CreateFriendshipRelationCommand command = new CreateFriendshipRelationCommand(
                friendshipRelationLinkRequest.getRelationInitiatorId(),
                friendshipRelationLinkRequest.getRelationMemberId()
        );
        FriendshipRelationEntity entity = createFriendshipRelationCommandHandler.handle(command);
        return ResponseEntity.ok(new FriendshipRelationPerformerResponse(entity.getId(), entity.getMembers()));
    }

    @PostMapping(value = "/unlink", produces = "application/json")
    @Operation(
            summary = "Cancel or remove a friendship relation",
            description = "Terminates an existing friendship relation (cancels pending request or removes established friendship)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Friendship relation terminated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FriendshipRelationPerformerResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid or missing user IDs", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found: No friendship relation exists between the specified users", content = @Content)
    public ResponseEntity<FriendshipRelationPerformerResponse> unlinkFriendshipRelation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "IDs of the initiator and target user",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FriendshipRelationPerformerRequest.class))
            )
            @RequestBody FriendshipRelationPerformerRequest friendshipRelationUnlinkRequest
    ) {
        TerminateFriendshipRelationCommand command = new TerminateFriendshipRelationCommand(
                friendshipRelationUnlinkRequest.getRelationInitiatorId(),
                friendshipRelationUnlinkRequest.getRelationMemberId()
        );
        FriendshipRelationEntity entity = terminateFriendshipRelationCommandHandler.handle(command);
        return ResponseEntity.ok(new FriendshipRelationPerformerResponse(entity.getId(), entity.getMembers()));
    }

    @PostMapping(value = "/approve", produces = "application/json")
    @Operation(
            summary = "Accept a friend request",
            description = "Approves a pending incoming friend request, establishing a mutual friendship."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Friend request approved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FriendshipRelationPerformerResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid or missing user IDs", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found: No pending friend request from the specified user", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict: Relation already approved or in invalid state", content = @Content)
    public ResponseEntity<FriendshipRelationPerformerResponse> approveFriendshipRelation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "IDs of the approver and the requester",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FriendshipRelationPerformerRequest.class))
            )
            @RequestBody FriendshipRelationPerformerRequest friendshipRelationUnlinkRequest
    ) {
        ApproveFriendshipRelationCommand command = new ApproveFriendshipRelationCommand(
                friendshipRelationUnlinkRequest.getRelationInitiatorId(),
                friendshipRelationUnlinkRequest.getRelationMemberId()
        );
        FriendshipRelationEntity entity = approveFriendShipRelationCommandHandler.handle(command);
        return ResponseEntity.ok(new FriendshipRelationPerformerResponse(entity.getId(), entity.getMembers()));
    }
}