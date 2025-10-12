package ru.solomka.profile.user;

import lombok.NonNull;
import ru.solomka.profile.common.EntityService;

public class UserSnapshotService extends EntityService<UserSnapshotEntity> {
    public UserSnapshotService(@NonNull UserSnapshotRepository userSnapshotRepository) {
        super(userSnapshotRepository);
    }
}
