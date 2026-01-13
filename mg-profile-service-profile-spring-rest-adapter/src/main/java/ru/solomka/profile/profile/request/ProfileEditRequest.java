package ru.solomka.profile.profile.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "Request to update a user's profile information")
public class ProfileEditRequest {

    @Schema(
            description = "Unique identifier of the profile to update",
            example = "550e8400-e29b-41d4-a716-446655440000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull UUID profileId;

    @Schema(
            description = "New unique user tag (username)",
            example = "johndoe_new",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull String userTag;

    @Schema(
            description = "Updated first name",
            example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull String firstName;

    @Schema(
            description = "Updated last name",
            example = "Doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull String lastName;

    @Schema(
            description = "Updated email address",
            example = "john.doe.updated@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NonNull String email;
}