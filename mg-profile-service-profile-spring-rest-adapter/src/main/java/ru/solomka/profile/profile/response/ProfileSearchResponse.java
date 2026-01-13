package ru.solomka.profile.profile.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.profile.profile.ProfileEntity;

import java.util.List;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Schema(description = "Wrapper containing a list of found user profiles")
public class ProfileSearchResponse {

    @Schema(
            description = "List of matching profiles (may be empty if no results found)",
            implementation = ProfileEntity.class
    )
    @NonNull List<ProfileEntity> profiles;
}