package ru.solomka.profile.profile.cqrs.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllProfilesByContainsFirstOrLastNameQuery {

    @NonNull String firstName;

    @NonNull String lastName;
}