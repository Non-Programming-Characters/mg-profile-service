package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetAllProfilesByContainsFirstOrLastNameQuery;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllProfilesByContainsFirstOrLastNameQueryHandler implements CommandHandler<GetAllProfilesByContainsFirstOrLastNameQuery, List<ProfileEntity>> {

    @NonNull ProfileService profileService;

    @Override
    public List<ProfileEntity> handle(GetAllProfilesByContainsFirstOrLastNameQuery command) {
        return profileService.getProfilesByFirstNameAndLastName(command.getFirstName(), command.getLastName());
    }
}