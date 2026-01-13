package ru.solomka.profile.profile.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.command.EditProfileInformationCommand;
import ru.solomka.profile.profile.exception.ProfileEditCommitChangesException;
import ru.solomka.profile.profile.exception.ProfileNotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EditProfileInformationCommandHandler implements CommandHandler<EditProfileInformationCommand, ProfileEntity> {

    @NonNull ProfileService profileService;

    @NonNull Duration profileBreakingChangesDuration;

    @Override
    public ProfileEntity handle(EditProfileInformationCommand command) {
        if(!profileService.existsById(command.getId()))
            throw new ProfileNotFoundException("Profile with id '%s' not found".formatted(command.getId()));

        ProfileEntity profileEntity = profileService.getById(command.getId());

        if(profileEntity.getLastEditedAt().isBefore(profileEntity.getLastEditedAt().plus(profileBreakingChangesDuration.toSeconds(), ChronoUnit.SECONDS)))
            throw new ProfileEditCommitChangesException("Profile change is prohibited until '%s'"
                    .formatted(
                            profileEntity.getLastEditedAt()
                                    .plus(profileBreakingChangesDuration.toSeconds(), ChronoUnit.SECONDS)
                                    .atZone(ZoneOffset.UTC)
                                    .format(DateTimeFormatter.ofPattern("dd.MM.yy"))
                    )
            );

        profileEntity.setUserTag(command.getUserTag().isEmpty() ? profileEntity.getUserTag() : command.getUserTag());
        profileEntity.setFirstName(command.getFirstName().isEmpty() ? profileEntity.getFirstName() : command.getFirstName());
        profileEntity.setLastName(command.getLastName().isEmpty() ? profileEntity.getLastName() : command.getLastName());
        profileEntity.setEmail(command.getEmail().isEmpty() ? profileEntity.getEmail() : command.getEmail());

        if(profileEntity == profileService.getById(command.getId()))
            return profileEntity;

        profileEntity.setLastEditedAt(Instant.now());

        return profileService.update(profileEntity);
    }
}