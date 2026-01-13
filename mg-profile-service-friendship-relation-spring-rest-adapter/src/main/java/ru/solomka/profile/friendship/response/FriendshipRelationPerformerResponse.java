package ru.solomka.profile.friendship.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "Details of the resulting friendship relation")
public class FriendshipRelationPerformerResponse {

    @Schema(
            description = "Unique identifier of the friendship relation",
            example = "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8"
    )
    @NonNull UUID id;

    @Schema(
            description = "List of user IDs participating in the friendship (always contains two elements)",
            example = "[\"550e8400-e29b-41d4-a716-446655440000\", \"f47ac10b-58cc-4372-a567-0e02b2c3d479\"]"
    )
    @NonNull List<UUID> members;
}