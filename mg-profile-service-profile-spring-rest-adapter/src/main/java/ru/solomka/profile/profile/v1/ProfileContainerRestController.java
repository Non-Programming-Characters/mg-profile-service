package ru.solomka.profile.profile.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Profile Search", description = "API for searching user profiles by various criteria")
public class ProfileContainerRestController {

    @NonNull CommandHandler<GetProfileByIdQuery, ProfileEntity> getProfileByIdQueryHandler;
    @NonNull CommandHandler<GetProfileByUserTagQuery, ProfileEntity> getProfileByUserTagQueryHandler;
    @NonNull CommandHandler<GetAllProfilesByContainsFirstOrLastNameQuery, List<ProfileEntity>> getProfilesByContainsFirstOrLastNameQueryHandler;

    @GetMapping(produces = "application/json")
    @Operation(
            summary = "Search profiles by different criteria",
            description = """
            Searches for user profiles using one of the following search modes:
            
            - `id`: exact match by UUID
            - `tag`: exact match by unique user tag (e.g., @johndoe)
            - `name`: partial match by first and/or last name (supports "John Doe" or just "John")
            """
    )
    @Parameter(
            name = "searchBy",
            description = "Search criterion: `id`, `tag`, or `name`",
            example = "name",
            required = true
    )
    @Parameter(
            name = "value",
            description = """
            Search value:
            - For `id`: UUID string (e.g., 550e8400-e29b-41d4-a716-446655440000)
            - For `tag`: username without '@' (e.g., johndoe)
            - For `name`: full name or part (e.g., "John", "John Doe")
            """,
            example = "John Doe",
            required = true
    )
    @ApiResponse(
            responseCode = "200",
            description = "Profiles found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileSearchResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid `searchBy` parameter or malformed value (e.g. invalid UUID)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not Found: No profile matches the given criteria", content = @Content)
    public ResponseEntity<ProfileSearchResponse> getProfileBySearchParam(
            @RequestParam("searchBy") String searchBy,
            @RequestParam("value") String value
    ) throws InvalidRequestException {
        return switch (searchBy.toLowerCase()) {
            case "id" -> {
                UUID id = UUID.fromString(value);
                ProfileEntity profile = getProfileByIdQueryHandler.handle(new GetProfileByIdQuery(id));
                yield ResponseEntity.ok(new ProfileSearchResponse(Collections.singletonList(profile)));
            }
            case "name" -> {
                String firstName = value.contains(" ") ? value.split(" ")[0] : value;
                String lastName = value.contains(" ") ? value.split(" ")[1] : "";
                List<ProfileEntity> profiles = getProfilesByContainsFirstOrLastNameQueryHandler.handle(
                        new GetAllProfilesByContainsFirstOrLastNameQuery(firstName, lastName)
                );
                yield ResponseEntity.ok(new ProfileSearchResponse(profiles));
            }
            case "tag" -> {
                ProfileEntity profile = getProfileByUserTagQueryHandler.handle(new GetProfileByUserTagQuery(value));
                yield ResponseEntity.ok(new ProfileSearchResponse(Collections.singletonList(profile)));
            }
            default -> throw new InvalidRequestException("Invalid search parameter: " + searchBy);
        };
    }
}