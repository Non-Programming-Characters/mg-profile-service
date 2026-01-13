package ru.solomka.profile.friendship.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;


@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "User IDs involved in a friendship action")
public class FriendshipRelationPerformerRequest {

    @Schema(
            description = "ID of the user initiating the action (sender of request or remover)",
            example = "550e8400-e29b-41d4-a716-446655440000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull UUID relationInitiatorId;

    @Schema(
            description = "ID of the target user (receiver of request or friend to remove)",
            example = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull UUID relationMemberId;
}