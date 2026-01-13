package ru.solomka.profile.friendship.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.friendship.RelationStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "Details of a friendship relation between users")
public class FriendshipRelationSearchResponse {

    @Schema(description = "Unique identifier of the friendship relation", example = "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8")
    @NonNull UUID id;

    @Schema(
            description = "List of user IDs in the relation (always contains two elements)",
            example = "[\"550e8400-e29b-41d4-a716-446655440000\", \"f47ac10b-58cc-4372-a567-0e02b2c3d479\"]"
    )
    @NonNull List<UUID> members;

    @Schema(
            description = "Current status of the friendship",
            allowableValues = {"APPROVED", "NOT_APPROVED"},
            example = "APPROVED"
    )
    @NonNull RelationStatus status;

    @Schema(description = "Timestamp when the relation was created", example = "2025-01-12T10:30:00Z")
    @NonNull Instant createdAt;
}