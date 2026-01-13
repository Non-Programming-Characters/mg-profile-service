package ru.solomka.profile.profile.v1;

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
public class ProfileEditorRestController {

    @NonNull CommandHandler<EditProfileInformationCommand, ProfileEntity> editProfileInformationCommandHandler;

    @PostMapping(produces = "application/json")
    public ResponseEntity<ProfileEntity> editProfile(@RequestBody ProfileEditRequest profileEditRequest) {
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