package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetProfileByProfileNameQuery;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetProfileByProfileNameQueryHandler implements CommandHandler<GetProfileByProfileNameQuery, ProfileEntity> {

    @NonNull ProfileService profileService;

    @Override
    public ProfileEntity handle(GetProfileByProfileNameQuery command) {
        if(command.getProfileName().isEmpty())
            throw new IllegalArgumentException("Argument 'profileName' cannot be empty");

        // TODO: ADD VALIDATION THE PROFILE-NAME ON SQL INJECTION (ADD REGULAR EXPRESSION)

        return profileService.getProfileByProfileName(command.getProfileName());
    }
}