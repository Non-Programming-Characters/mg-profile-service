package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetProfilesByFirstNameAndLastNameQuery;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetProfilesByFirstNameAndLastNameQueryHandler implements CommandHandler<GetProfilesByFirstNameAndLastNameQuery, List<ProfileEntity>> {

    @NonNull ProfileService profileService;

    @Override
    public List<ProfileEntity> handle(GetProfilesByFirstNameAndLastNameQuery command) {

        if(command.getFirstName().isEmpty())
            throw new IllegalArgumentException("Argument 'firstName' cannot be empty");

        if(command.getLastName().isEmpty())
            throw new IllegalArgumentException("Argument 'lastName' cannot be empty");

        return profileService.getProfilesByFirstNameAndLastName(command.getFirstName(), command.getLastName());
    }
}
