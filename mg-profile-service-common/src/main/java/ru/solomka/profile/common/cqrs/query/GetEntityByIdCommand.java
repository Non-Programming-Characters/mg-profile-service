package ru.solomka.profile.common.cqrs.query;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ToString
public class GetEntityByIdCommand {

    @NonNull UUID id;
}