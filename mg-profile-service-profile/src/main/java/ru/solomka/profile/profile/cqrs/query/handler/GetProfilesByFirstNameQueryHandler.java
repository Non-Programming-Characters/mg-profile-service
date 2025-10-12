package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetProfilesByFirstNameQuery;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetProfilesByFirstNameQueryHandler implements CommandHandler<GetProfilesByFirstNameQuery, List<ProfileEntity>> {

    @NonNull ProfileService profileService;

    @Override
    public List<ProfileEntity> handle(GetProfilesByFirstNameQuery command) {
        if(command.getFirstName().isEmpty())
            throw new IllegalArgumentException("Argument 'firstName' cannot be empty");

        return profileService.getProfilesByFirstName(command.getFirstName());
    }
}
