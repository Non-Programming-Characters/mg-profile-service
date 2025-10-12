package ru.solomka.profile.profile.v1;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.cqrs.query.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/profile")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileContainerRestController {

    @NonNull CommandHandler<GetProfileByProfileNameQuery, ProfileEntity> getProfileByProfileNameQueryHandler;
    @NonNull CommandHandler<GetProfileByIdQuery, ProfileEntity> getProfileByIdQueryHandler;
    @NonNull CommandHandler<GetProfilesByFirstNameQuery, List<ProfileEntity>> getProfilesByFirstNameQueryHandler;
    @NonNull CommandHandler<GetProfilesByLastNameQuery, List<ProfileEntity>> getProfilesByLastNameQueryHandler;
    @NonNull CommandHandler<GetProfilesByFirstNameAndLastNameQuery, List<ProfileEntity>> getProfilesByFirstNameAndLastNameQueryHandler;

    @GetMapping(produces = "application/json")
    public ResponseEntity<ProfileEntity> getProfileById(@RequestParam("profileId") UUID profileId) {
        GetProfileByIdQuery getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        return ResponseEntity.ok(getProfileByIdQueryHandler.handle(getProfileByIdQuery));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ProfileEntity> getProfileByName(@RequestParam("profileName") String profileName) {
        GetProfileByProfileNameQuery getProfileByProfileNameQuery = new GetProfileByProfileNameQuery(profileName);
        return ResponseEntity.ok(getProfileByProfileNameQueryHandler.handle(getProfileByProfileNameQuery));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProfileEntity>> getProfilesByFirstName(@RequestParam("firstName") String firstName) {
        GetProfilesByFirstNameQuery getProfilesByFirstNameQuery = new GetProfilesByFirstNameQuery(firstName);
        return ResponseEntity.ok(getProfilesByFirstNameQueryHandler.handle(getProfilesByFirstNameQuery));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProfileEntity>> getProfilesByLastName(@RequestParam("lastName") String lastName) {
        GetProfilesByLastNameQuery getProfilesByLastNameQuery = new GetProfilesByLastNameQuery(lastName);
        return ResponseEntity.ok(getProfilesByLastNameQueryHandler.handle(getProfilesByLastNameQuery));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProfileEntity>> getProfilesByLastName(@RequestParam("firstName") String firstName,
                                                                     @RequestParam("lastName") String lastName) {
        GetProfilesByFirstNameAndLastNameQuery getProfilesByFirstNameAndLastNameQuery = new GetProfilesByFirstNameAndLastNameQuery(firstName, lastName);
        return ResponseEntity.ok(getProfilesByFirstNameAndLastNameQueryHandler.handle(getProfilesByFirstNameAndLastNameQuery));
    }


}