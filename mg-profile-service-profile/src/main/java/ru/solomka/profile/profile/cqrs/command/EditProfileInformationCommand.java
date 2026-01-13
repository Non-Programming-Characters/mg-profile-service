package ru.solomka.profile.profile.cqrs.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EditProfileInformationCommand {

    @NonNull UUID id;

    @NonNull String userTag;

    @NonNull String firstName;

    @NonNull String lastName;

    @NonNull String email;
}