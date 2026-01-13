package ru.solomka.profile.profile;

import org.springframework.data.jpa.repository.Query;
import ru.solomka.profile.common.BaseCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudProfileRepository extends BaseCrudRepository<JpaProfileEntity> {

    @Query("select jpe from JpaProfileEntity jpe where lower(jpe.userTag) = lower(:userTag)")
    Optional<JpaProfileEntity> findProfileByUserTag(String userTag);

    @Query("select jpe from JpaProfileEntity jpe " +
            "WHERE jpe.firstName IS NOT NULL and LOWER(jpe.firstName) LIKE CONCAT('%',LOWER(:firstName),'%') " +
            "OR jpe.lastName IS NOT NULL and LOWER(jpe.lastName) LIKE CONCAT('%',LOWER(:lastName),'%')")
    List<JpaProfileEntity> getProfilesByContainsFirstOrLastName(String firstName, String lastName);

    @Query("select count(jpe) > 0 from JpaProfileEntity jpe where jpe.userTag = :userTag")
    boolean existsByUserTag(String userTag);
}