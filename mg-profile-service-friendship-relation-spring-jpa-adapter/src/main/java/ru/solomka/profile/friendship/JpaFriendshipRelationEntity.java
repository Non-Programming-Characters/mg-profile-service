package ru.solomka.profile.friendship;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "friendship_relation")
@Entity
public class JpaFriendshipRelationEntity {

    @Id
    @Column(name = "id", nullable = false)
    @NonNull UUID id;

    @Column(name = "initiator_id", nullable = false)
    @NonNull UUID initiator;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "relation_members",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "user_id", nullable = false)
    List<UUID> members = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull RelationStatus status;

    @Column(name = "created_at", nullable = false)
    @NonNull Instant createdAt;
}