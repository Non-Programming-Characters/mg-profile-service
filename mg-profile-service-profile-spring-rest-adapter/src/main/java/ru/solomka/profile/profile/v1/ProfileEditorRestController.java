package ru.solomka.profile.profile.v1;

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
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.cqrs.command.EditProfileInformationCommand;
import ru.solomka.profile.profile.request.ProfileEditRequest;

@RestController
@RequestMapping("/api/v1/profile/edit")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Profile Management", description = "API for updating user profile information")
public class ProfileEditorRestController {

    @NonNull CommandHandler<EditProfileInformationCommand, ProfileEntity> editProfileInformationCommandHandler;

    @PostMapping(produces = "application/json")
    @Operation(
            summary = "Update user profile information",
            description = "Modifies profile details such as user tag, first name, last name, and email."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Profile updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileEntity.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid or missing fields (e.g., malformed email, invalid user tag format)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found: Profile with the specified ID does not exist", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict: User tag or email is already in use by another profile", content = @Content)
    public ResponseEntity<ProfileEntity> editProfile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated profile data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProfileEditRequest.class))
            )
            @RequestBody ProfileEditRequest profileEditRequest
    ) {
        return ResponseEntity.ok(editProfileInformationCommandHandler.handle(
                new EditProfileInformationCommand(
                        profileEditRequest.getProfileId(),
                        profileEditRequest.getUserTag(),
                        profileEditRequest.getFirstName(),
                        profileEditRequest.getLastName(),
                        profileEditRequest.getEmail()
                )
        ));
    }
}