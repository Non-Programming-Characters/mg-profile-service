package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetProfileByIdQuery;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetProfileByIdQueryHandler implements CommandHandler<GetProfileByIdQuery, ProfileEntity> {

    @NonNull ProfileService profileService;

    @Override
    public ProfileEntity handle(GetProfileByIdQuery command) {
        return profileService.getById(command.getId());
    }
}
