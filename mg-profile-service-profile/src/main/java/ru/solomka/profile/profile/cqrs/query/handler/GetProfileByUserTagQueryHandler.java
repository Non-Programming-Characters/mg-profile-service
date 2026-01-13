package ru.solomka.profile.profile.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.profile.cqrs.query.GetProfileByUserTagQuery;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProfileByUserTagQueryHandler implements CommandHandler<GetProfileByUserTagQuery, ProfileEntity> {

    @NonNull ProfileService profileService;

    @Override
    public ProfileEntity handle(GetProfileByUserTagQuery command) {
        if(command.getUserTag().isEmpty())
            throw new EntityNotFoundException("Profile user tag cannot be empty");

        return profileService.getProfileByUserTag(command.getUserTag());
    }
}
