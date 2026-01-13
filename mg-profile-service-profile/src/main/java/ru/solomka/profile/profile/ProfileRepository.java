package ru.solomka.profile.profile;

import lombok.NonNull;
import ru.solomka.profile.common.EntityRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends EntityRepository<ProfileEntity> {
    Optional<ProfileEntity> findProfileByUserTag(String userTag);
    List<ProfileEntity> getProfilesByContainsFirstOrLastName(@NonNull String firstName, String lastName);

    boolean existsByUserTag(String userTag);
}