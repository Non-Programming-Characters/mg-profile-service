package ru.solomka.profile.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.friendship.FriendshipRelationEntity;
import ru.solomka.profile.friendship.FriendshipRelationRepository;
import ru.solomka.profile.friendship.FriendshipRelationService;
import ru.solomka.profile.friendship.RelationStatus;
import ru.solomka.profile.friendship.exception.RelationNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FriendshipRelationServiceTest {

    @Mock
    private FriendshipRelationRepository friendshipRelationRepository;

    private FriendshipRelationService friendshipRelationService;

    private UUID memberA;
    private UUID memberB;
    private FriendshipRelationEntity relationEntity;

    @BeforeEach
    void setUp() {
        friendshipRelationService = new FriendshipRelationService(friendshipRelationRepository);

        memberA = UUID.randomUUID();
        memberB = UUID.randomUUID();
        relationEntity = FriendshipRelationEntity.builder()
                .id(UUID.randomUUID())
                .initiator(memberA)
                .members(List.of(memberA, memberB))
                .status(RelationStatus.NOT_APPROVED)
                .createdAt(Instant.now())
                .build();
    }

    @Nested
    @DisplayName("Работа с сущностью по ID (наследуемое поведение)")
    class InheritedEntityOperations {

        @Test
        @DisplayName("Возвращает сущность по ID, если она существует")
        void shouldReturnEntityByIdWhenExists() {
            UUID entityId = relationEntity.getId();
            given(friendshipRelationRepository.findById(entityId)).willReturn(Optional.of(relationEntity));

            FriendshipRelationEntity result = friendshipRelationService.getById(entityId);

            assertThat(result).isEqualTo(relationEntity);
        }

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException при получении несуществующей сущности по ID")
        void shouldThrowEntityNotFoundExceptionWhenGettingNonExistentById() {
            UUID nonExistentId = UUID.randomUUID();
            given(friendshipRelationRepository.findById(nonExistentId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> friendshipRelationService.getById(nonExistentId))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @Test
        @DisplayName("Удаляет сущность по ID и возвращает её")
        void shouldDeleteEntityByIdAndReturnIt() {
            UUID entityId = relationEntity.getId();
            given(friendshipRelationRepository.existsById(entityId)).willReturn(true);

            friendshipRelationService.deleteById(entityId);

            verify(friendshipRelationRepository).deleteById(entityId);
        }

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException при удалении несуществующей сущности по ID")
        void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentById() {
            UUID nonExistentId = UUID.randomUUID();
            given(friendshipRelationRepository.existsById(nonExistentId)).willReturn(false);

            assertThatThrownBy(() -> friendshipRelationService.deleteById(nonExistentId))
                    .isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение дружеской связи по ID участников")
    class GetByMembersId {

        @Test
        @DisplayName("Возвращает связь, если она существует")
        void shouldReturnRelationWhenExists() {
            given(friendshipRelationRepository.findFriendshipRelationByRelationMembersId(memberA, memberB))
                    .willReturn(Optional.of(relationEntity));

            FriendshipRelationEntity result = friendshipRelationService.getFriendshipRelationByMembersId(memberA, memberB);

            assertThat(result).isEqualTo(relationEntity);
        }

        @Test
        @DisplayName("Выбрасывает RelationNotFoundException, если связь не найдена")
        void shouldThrowRelationNotFoundExceptionWhenNotFound() {
            given(friendshipRelationRepository.findFriendshipRelationByRelationMembersId(memberA, memberB))
                    .willReturn(Optional.empty());

            assertThatThrownBy(() -> friendshipRelationService.getFriendshipRelationByMembersId(memberA, memberB))
                    .isInstanceOf(RelationNotFoundException.class)
                    .hasMessageContaining(memberA.toString())
                    .hasMessageContaining(memberB.toString());
        }
    }

    @Nested
    @DisplayName("Поиск дружеской связи по ID участников (возвращающий Optional)")
    class FindByMembersId {

        @Test
        @DisplayName("Возвращает Optional с сущностью, если связь существует")
        void shouldReturnOptionalWithEntityWhenExists() {
            given(friendshipRelationRepository.findFriendshipRelationByRelationMembersId(memberA, memberB))
                    .willReturn(Optional.of(relationEntity));

            Optional<FriendshipRelationEntity> result = friendshipRelationService
                    .findFriendshipRelationByRelationMembersId(memberA, memberB);

            assertThat(result).contains(relationEntity);
        }

        @Test
        @DisplayName("Возвращает Optional.empty, если связь не найдена")
        void shouldReturnEmptyOptionalWhenNotFound() {
            given(friendshipRelationRepository.findFriendshipRelationByRelationMembersId(memberA, memberB))
                    .willReturn(Optional.empty());

            Optional<FriendshipRelationEntity> result = friendshipRelationService
                    .findFriendshipRelationByRelationMembersId(memberA, memberB);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Получение списка связей по участнику и статусу")
    class GetRelationsByMemberAndStatus {

        @Test
        @DisplayName("Возвращает список связей для участника с заданным статусом")
        void shouldReturnRelationsForMemberWithStatus() {
            RelationStatus status = RelationStatus.NOT_APPROVED;
            List<FriendshipRelationEntity> expected = List.of(relationEntity);
            given(friendshipRelationRepository.getFriendshipRelationsByRelationMemberId(memberA, status))
                    .willReturn(expected);

            List<FriendshipRelationEntity> result = friendshipRelationService
                    .getFriendshipRelationsByRelationMemberId(memberA, status);

            assertThat(result).containsExactlyElementsOf(expected);
        }
    }

    @Nested
    @DisplayName("Получение списка исходящих запросов по инициатору и статусу")
    class GetRelationsByInitiatorAndStatus {

        @Test
        @DisplayName("Возвращает список исходящих связей для инициатора с заданным статусом")
        void shouldReturnOutgoingRelationsForInitiatorWithStatus() {
            RelationStatus status = RelationStatus.NOT_APPROVED;
            List<FriendshipRelationEntity> expected = List.of(relationEntity);
            given(friendshipRelationRepository.getFriendshipRelationsByRelationInitiatorId(memberA, status))
                    .willReturn(expected);

            List<FriendshipRelationEntity> result = friendshipRelationService
                    .getFriendshipRelationsByRelationInitiatorId(memberA, status);

            assertThat(result).containsExactlyElementsOf(expected);
        }
    }

    @Nested
    @DisplayName("Удаление дружеской связи по ID участников")
    class DeleteByMembersId {

        @Test
        @DisplayName("Удаляет связь и возвращает удалённую сущность")
        void shouldDeleteRelationAndReturnIt() {
            given(friendshipRelationRepository.findFriendshipRelationByRelationMembersId(memberA, memberB))
                    .willReturn(Optional.of(relationEntity));

            FriendshipRelationEntity deleted = friendshipRelationService
                    .deleteFriendshipRelationByRelationMembersId(memberA, memberB);

            assertThat(deleted).isEqualTo(relationEntity);
            verify(friendshipRelationRepository).deleteFriendshipRelationByRelationMembersId(memberA, memberB);
        }

        @Test
        @DisplayName("Выбрасывает RelationNotFoundException при удалении несуществующей связи")
        void shouldThrowRelationNotFoundExceptionWhenDeletingNonExistentRelation() {
            given(friendshipRelationRepository.findFriendshipRelationByRelationMembersId(memberA, memberB))
                    .willReturn(Optional.empty());

            assertThatThrownBy(() ->
                    friendshipRelationService.deleteFriendshipRelationByRelationMembersId(memberA, memberB))
                    .isInstanceOf(RelationNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Проверка существования связи")
    class ExistsByMembersId {

        @Test
        @DisplayName("Возвращает true, если связь существует")
        void shouldReturnTrueWhenRelationExists() {
            given(friendshipRelationRepository.existsByRelationMembersId(memberA, memberB)).willReturn(true);

            boolean result = friendshipRelationService.existsByRelationMembersId(memberA, memberB);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Возвращает false, если связь не существует")
        void shouldReturnFalseWhenRelationDoesNotExist() {
            given(friendshipRelationRepository.existsByRelationMembersId(memberA, memberB)).willReturn(false);

            boolean result = friendshipRelationService.existsByRelationMembersId(memberA, memberB);

            assertThat(result).isFalse();
        }
    }
}
