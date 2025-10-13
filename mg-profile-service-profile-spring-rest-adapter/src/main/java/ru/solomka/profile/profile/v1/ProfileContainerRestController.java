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
@RequestMapping("/v1/api/profile/container/search")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileContainerRestController {

    @NonNull CommandHandler<GetProfileByProfileNameQuery, ProfileEntity> getProfileByProfileNameQueryHandler;
    @NonNull CommandHandler<GetProfileByIdQuery, ProfileEntity> getProfileByIdQueryHandler;
    @NonNull CommandHandler<GetProfilesByFirstNameQuery, List<ProfileEntity>> getProfilesByFirstNameQueryHandler;
    @NonNull CommandHandler<GetProfilesByLastNameQuery, List<ProfileEntity>> getProfilesByLastNameQueryHandler;

    @GetMapping(value = "/spec", produces = "application/json")
    public ResponseEntity<ProfileEntity> getProfileBySearchParam(@RequestParam("searchBy") String searchBy, @RequestParam("value") Object value) {
        switch (searchBy) {
            case "id" -> {
                GetProfileByIdQuery getProfileByIdQuery = new GetProfileByIdQuery(UUID.fromString(String.valueOf(value)));
                return ResponseEntity.ok(getProfileByIdQueryHandler.handle(getProfileByIdQuery));
            }
            case "profileName" -> {
                GetProfileByProfileNameQuery getProfileByProfileNameQuery = new GetProfileByProfileNameQuery(String.valueOf(value));
                return ResponseEntity.ok(getProfileByProfileNameQueryHandler.handle(getProfileByProfileNameQuery));
            }
            default -> throw new IllegalArgumentException("Invalid search parameter");
        }
    }

    @GetMapping(value = "/multiple", produces = "application/json")
    public ResponseEntity<List<ProfileEntity>> getProfilesBySearchParam(@RequestParam("searchBy") String searchBy,  @RequestParam("value") Object value) {
        switch (searchBy) {
            case "firstName" -> {
                GetProfilesByFirstNameQuery getProfilesByFirstNameQuery = new GetProfilesByFirstNameQuery(String.valueOf(value));
                return ResponseEntity.ok(getProfilesByFirstNameQueryHandler.handle(getProfilesByFirstNameQuery));
            }
            case "lastName" -> {
                GetProfilesByLastNameQuery getProfilesByLastNameQuery = new GetProfilesByLastNameQuery(String.valueOf(value));
                return ResponseEntity.ok(getProfilesByLastNameQueryHandler.handle(getProfilesByLastNameQuery));
            }
            default -> throw new IllegalArgumentException("Invalid search parameter");
        }
    }
}