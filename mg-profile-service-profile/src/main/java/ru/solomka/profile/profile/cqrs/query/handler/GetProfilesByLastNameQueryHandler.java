package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetProfilesByLastNameQuery;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetProfilesByLastNameQueryHandler implements CommandHandler<GetProfilesByLastNameQuery, List<ProfileEntity>> {

    @NonNull ProfileService profileService;

    @Override
    public List<ProfileEntity> handle(GetProfilesByLastNameQuery command) {

        if(command.getLastName().isEmpty())
            throw new IllegalArgumentException("Argument 'lastName' cannot be empty");

        return profileService.getProfilesByFirstName(command.getLastName());
    }
}
