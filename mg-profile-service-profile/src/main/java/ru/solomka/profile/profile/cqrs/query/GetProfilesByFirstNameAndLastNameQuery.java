package ru.solomka.profile.profile.cqrs.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetProfilesByFirstNameAndLastNameQuery {

    @NonNull String firstName;

    @NonNull String lastName;
}
