package ru.solomka.profile.friendship.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.friendship.RelationStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendshipRelationSearchResponse {

    @NonNull UUID id;

    @NonNull
    List<UUID> members;

    @NonNull
    RelationStatus status;

    @NonNull
    Instant createdAt;
}