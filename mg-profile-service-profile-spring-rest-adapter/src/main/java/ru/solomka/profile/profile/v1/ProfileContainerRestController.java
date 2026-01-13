package ru.solomka.profile.profile.v1;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.profile.common.cqrs.CommandHandler;
import ru.solomka.profile.common.exception.InvalidRequestException;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.cqrs.query.*;
import ru.solomka.profile.profile.response.ProfileSearchResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile/search")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileContainerRestController {

    @NonNull CommandHandler<GetProfileByIdQuery, ProfileEntity> getProfileByIdQueryHandler;
    @NonNull CommandHandler<GetProfileByUserTagQuery, ProfileEntity> getProfileByUserTagQueryHandler;
    @NonNull CommandHandler<GetAllProfilesByContainsFirstOrLastNameQuery, List<ProfileEntity>> getProfilesByContainsFirstOrLastNameQueryHandler;

    @GetMapping(produces = "application/json")
    public ResponseEntity<ProfileSearchResponse> getProfileBySearchParam(@RequestParam("searchBy") String searchBy, @RequestParam("value") Object value) throws InvalidRequestException {
        switch (searchBy) {
            case "id" -> {
                GetProfileByIdQuery getProfileByIdQuery = new GetProfileByIdQuery(UUID.fromString(String.valueOf(value)));
                return ResponseEntity.ok(new ProfileSearchResponse(Collections.singletonList(getProfileByIdQueryHandler.handle(getProfileByIdQuery))));
            }
            case "name" -> {
                String convertedValue = String.valueOf(value);
                GetAllProfilesByContainsFirstOrLastNameQuery getAllProfilesByContainsFirstOrLastNameQuery = new GetAllProfilesByContainsFirstOrLastNameQuery(
                       convertedValue.contains(" ") ? convertedValue.split(" ")[0] : convertedValue,
                       convertedValue.contains(" ") ? convertedValue.split(" ")[1] : ""
                );
                return ResponseEntity.ok(new ProfileSearchResponse(getProfilesByContainsFirstOrLastNameQueryHandler.handle(getAllProfilesByContainsFirstOrLastNameQuery)));
            }
            case "tag" -> {
                GetProfileByUserTagQuery getProfileByIdQuery = new GetProfileByUserTagQuery(String.valueOf(value));
                return ResponseEntity.ok(new ProfileSearchResponse(Collections.singletonList(getProfileByUserTagQueryHandler.handle(getProfileByIdQuery))));
            }
            default -> throw new InvalidRequestException("Invalid search parameter");
        }
    }
}