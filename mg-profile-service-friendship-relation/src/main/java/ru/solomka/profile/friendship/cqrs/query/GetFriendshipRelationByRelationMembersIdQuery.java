package ru.solomka.profile.friendship.cqrs.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetFriendshipRelationByRelationMembersIdQuery {

    @NonNull UUID relationFirstMemberId;

    @NonNull UUID relationSecondMemberId;
}