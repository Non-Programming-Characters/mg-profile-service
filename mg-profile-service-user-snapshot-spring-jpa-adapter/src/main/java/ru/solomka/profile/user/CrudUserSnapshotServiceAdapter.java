package ru.solomka.profile.user;

import lombok.NonNull;
import ru.solomka.profile.common.BaseJpaRepositoryAdapter;
import ru.solomka.profile.common.mapper.Mapper;

public class CrudUserSnapshotServiceAdapter extends BaseJpaRepositoryAdapter<JpaUserSnapshotEntity, UserSnapshotEntity> implements UserSnapshotRepository {
    public CrudUserSnapshotServiceAdapter(@NonNull CrudUserSnapshotRepository repository,
                                          @NonNull Mapper<JpaUserSnapshotEntity, UserSnapshotEntity> mapper) {
        super(repository, mapper);
    }
}