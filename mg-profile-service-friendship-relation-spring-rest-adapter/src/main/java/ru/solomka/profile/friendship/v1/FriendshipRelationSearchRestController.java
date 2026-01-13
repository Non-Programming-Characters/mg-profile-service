package ru.solomka.profile.friendship.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Friendship Search", description = "API for querying friendship relations between users")
public class FriendshipRelationSearchRestController {

    @NonNull CommandHandler<GetFriendshipRelationsByRelationMemberIdQuery, List<FriendshipRelationEntity>> friendshipRelationsByRelationMemberIdQueryHandler;
    @NonNull CommandHandler<GetFriendshipRelationsByRelationInitiatorIdQuery, List<FriendshipRelationEntity>> friendshipRelationsByRelationInitiatorIdQueryHandler;
    @NonNull CommandHandler<GetFriendshipRelationByRelationMembersIdQuery, FriendshipRelationEntity> friendshipRelationByRelationMembersIdQueryHandler;

    @GetMapping(value = "/search", produces = "application/json")
    @Operation(
            summary = "Get friendship relation between two specific users",
            description = "Retrieves the exact friendship relation between two given users."
    )
    @Parameter(
            name = "relationFirstMemberId",
            description = "ID of the first user",
            example = "550e8400-e29b-41d4-a716-446655440000",
            required = true
    )
    @Parameter(
            name = "relationSecondMemberId",
            description = "ID of the second user",
            example = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
            required = true
    )
    @ApiResponse(
            responseCode = "200",
            description = "Friendship relation found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FriendshipRelationSearchResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid or missing user IDs", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found: No friendship relation exists between the specified users", content = @Content)
    public ResponseEntity<FriendshipRelationSearchResponse> searchFriendshipBetweenMembers(
            @RequestParam(value = "relationFirstMemberId") UUID relationFirstMemberId,
            @RequestParam(value = "relationSecondMemberId") UUID relationSecondMemberId
    ) throws InvalidRequestException {
        GetFriendshipRelationByRelationMembersIdQuery query = new GetFriendshipRelationByRelationMembersIdQuery(
                relationFirstMemberId, relationSecondMemberId
        );
        FriendshipRelationEntity entity = friendshipRelationByRelationMembersIdQueryHandler.handle(query);
        return ResponseEntity.ok(new FriendshipRelationSearchResponse(
                entity.getId(), entity.getMembers(), entity.getStatus(), entity.getCreatedAt()
        ));
    }

    @GetMapping(value = "/{userId}/search", produces = "application/json")
    @Operation(
            summary = "List friendship relations for a user",
            description = """
            Retrieves all friendship relations for a given user, filtered by role and status.
            
            Supported roles:
            - `initiator`: relations where the user sent the request
            - `member`: relations where the user is the target (or mutual friend)
            
            Supported statuses: `APPROVED`, `NOT_APPROVED`
            """
    )
    @Parameter(
            name = "userId",
            description = "ID of the user to search relations for",
            example = "550e8400-e29b-41d4-a716-446655440000",
            required = true
    )
    @Parameter(
            name = "type",
            description = "Role of the user in the relation: `initiator` or `member`",
            example = "initiator",
            required = true
    )
    @Parameter(
            name = "status",
            description = "Filter by relation status (e.g., `APPROVED`, `NOT_APPROVED`)",
            example = "APPROVED",
            required = true
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of matching friendship relations",
            content = @Content(mediaType = "application/json", array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = FriendshipRelationSearchResponse.class)))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid `type` or `status` parameter", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found: User does not exist", content = @Content)
    public ResponseEntity<List<FriendshipRelationSearchResponse>> localFriendshipRelationSearch(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "status") String status
    ) {
        RelationStatus relationStatus = RelationStatus.valueOf(status.toUpperCase());
        return switch (type.toLowerCase()) {
            case "initiator" -> {
                GetFriendshipRelationsByRelationInitiatorIdQuery query = new GetFriendshipRelationsByRelationInitiatorIdQuery(userId, relationStatus);
                List<FriendshipRelationEntity> entities = friendshipRelationsByRelationInitiatorIdQueryHandler.handle(query);
                yield ResponseEntity.ok(entities.stream()
                        .map(e -> new FriendshipRelationSearchResponse(e.getId(), e.getMembers(), e.getStatus(), e.getCreatedAt()))
                        .toList());
            }
            case "member" -> {
                GetFriendshipRelationsByRelationMemberIdQuery query = new GetFriendshipRelationsByRelationMemberIdQuery(userId, relationStatus);
                List<FriendshipRelationEntity> entities = friendshipRelationsByRelationMemberIdQueryHandler.handle(query);
                yield ResponseEntity.ok(entities.stream()
                        .map(e -> new FriendshipRelationSearchResponse(e.getId(), e.getMembers(), e.getStatus(), e.getCreatedAt()))
                        .toList());
            }
            default -> throw new IllegalArgumentException("Invalid search type parameter: " + type);
        };
    }
}