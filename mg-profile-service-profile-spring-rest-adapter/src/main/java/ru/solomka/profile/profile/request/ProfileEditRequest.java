package ru.solomka.profile.profile.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileEditRequest {

    @NonNull UUID profileId;

    @NonNull String userTag;

    @NonNull String firstName;

    @NonNull String lastName;

    @NonNull String email;
}