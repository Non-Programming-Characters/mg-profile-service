package ru.solomka.profile.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solomka.profile.common.EntityNotification;
import ru.solomka.profile.common.exception.EntityNotFoundException;
import ru.solomka.profile.profile.ProfileEntity;
import ru.solomka.profile.profile.ProfileRepository;
import ru.solomka.profile.profile.ProfileService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock private ProfileRepository profileRepository;

    @Mock private EntityNotification<ProfileEntity> profileNotification;

    private ProfileService profileService;

    private ProfileEntity sampleProfile;

    @BeforeEach
    void setUp() {
        profileService = new ProfileService(profileRepository, profileNotification);

        sampleProfile = ProfileEntity.builder()
                .id(UUID.randomUUID())
                .userTag("john_doe")
                .firstName("John")
                .lastName("Doe")
                .email("sample@mail.ru")
                .lastEditedAt(Instant.now())
                .createdAt(Instant.now())
                .build();
    }

    @Nested
    @DisplayName("Создание профиля")
    class CreateProfile {

        @Test
        @DisplayName("Создаёт профиль и отправляет уведомление о создании")
        void shouldCreateProfileAndNotify() {
            ProfileEntity newProfile = ProfileEntity.builder()
                    .userTag("alice_smith")
                    .firstName("Alice")
                    .lastName("Smith")
                    .email("sample@mail.ru")
                    .lastEditedAt(Instant.now())
                    .createdAt(Instant.now())
                    .build();

            ProfileEntity savedProfile = ProfileEntity.builder()
                    .id(UUID.randomUUID())
                    .userTag("alice_smith")
                    .firstName("Alice")
                    .lastName("Smith")
                    .email("sample@mail.ru")
                    .lastEditedAt(Instant.now())
                    .createdAt(Instant.now())
                    .build();

            given(profileRepository.create(newProfile)).willReturn(savedProfile);

            ProfileEntity result = profileService.create(newProfile);

            assertThat(result).isEqualTo(savedProfile);
            verify(profileNotification).notifyCreate(savedProfile);
        }
    }

    @Nested
    @DisplayName("Обновление профиля")
    class UpdateProfile {

        @Test
        @DisplayName("Обновляет профиль и отправляет уведомление об обновлении")
        void shouldUpdateProfileAndNotify() {
            ProfileEntity updatedProfile = ProfileEntity.builder()
                    .id(sampleProfile.getId())
                    .userTag("john_newtag")
                    .firstName("John")
                    .lastName("Doe")
                    .email("sample@mail.ru")
                    .lastEditedAt(Instant.now())
                    .build();

            given(profileRepository.update(updatedProfile)).willReturn(updatedProfile);

            ProfileEntity result = profileService.update(updatedProfile);

            assertThat(result).isEqualTo(updatedProfile);
        }
    }

    @Nested
    @DisplayName("Удаление профиля по ID")
    class DeleteProfileById {

        @Test
        @DisplayName("Удаляет существующий профиль и отправляет уведомление об удалении")
        void shouldDeleteExistingProfileAndNotify() {
            ProfileEntity profileEntity = ProfileEntity.builder()
                    .id(UUID.randomUUID())
                    .userTag("john_doe")
                    .firstName("John")
                    .lastName("Doe")
                    .email("sample@mail.ru")
                    .lastEditedAt(Instant.now())
                    .createdAt(Instant.now())
                    .build();

            UUID profileId = profileEntity.getId();

            given(profileRepository.existsById(profileId)).willReturn(true);
            given(profileRepository.deleteById(profileId)).willReturn(profileEntity);

            ProfileEntity deletedProfile = profileService.deleteById(profileId);

            verify(profileRepository).deleteById(profileId);
            verify(profileNotification).notifyDelete(deletedProfile);
        }

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException при удалении несуществующего профиля")
        void shouldThrowEntityNotFoundExceptionWhenProfileNotFound() {
            UUID nonExistentId = UUID.randomUUID();
            given(profileRepository.existsById(nonExistentId)).willReturn(false);

            assertThatThrownBy(() -> profileService.deleteById(nonExistentId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining(nonExistentId.toString());
        }
    }

    @Nested
    @DisplayName("Получение профиля по userTag")
    class GetProfileByUserTag {

        @Test
        @DisplayName("Возвращает профиль, если он существует")
        void shouldReturnProfileWhenExists() {
            String userTag = "john_doe";
            given(profileRepository.findProfileByUserTag(userTag)).willReturn(Optional.of(sampleProfile));

            ProfileEntity result = profileService.getProfileByUserTag(userTag);

            assertThat(result).isEqualTo(sampleProfile);
        }

        @Test
        @DisplayName("Выбрасывает EntityNotFoundException, если профиль не найден")
        void shouldThrowEntityNotFoundExceptionWhenProfileNotFound() {
            String userTag = "nonexistent_tag";
            given(profileRepository.findProfileByUserTag(userTag)).willReturn(Optional.empty());

            assertThatThrownBy(() -> profileService.getProfileByUserTag(userTag))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining(userTag);
        }
    }

    @Nested
    @DisplayName("Поиск профиля по userTag (возвращающий Optional)")
    class FindProfileByUserTag {

        @Test
        @DisplayName("Возвращает Optional с профилем, если он существует")
        void shouldReturnOptionalWithProfileWhenExists() {
            String userTag = "john_doe";
            given(profileRepository.findProfileByUserTag(userTag)).willReturn(Optional.of(sampleProfile));

            Optional<ProfileEntity> result = profileService.findProfileByUserTag(userTag);

            assertThat(result).contains(sampleProfile);
        }

        @Test
        @DisplayName("Возвращает Optional.empty, если профиль не найден")
        void shouldReturnEmptyOptionalWhenProfileNotFound() {
            String userTag = "unknown";
            given(profileRepository.findProfileByUserTag(userTag)).willReturn(Optional.empty());

            Optional<ProfileEntity> result = profileService.findProfileByUserTag(userTag);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Поиск профилей по имени или фамилии")
    class SearchProfilesByName {

        @Test
        @DisplayName("Возвращает список профилей, соответствующих имени или фамилии")
        void shouldReturnMatchingProfiles() {
            String firstName = "John";
            String lastName = "Doe";
            List<ProfileEntity> expected = List.of(sampleProfile);
            given(profileRepository.getProfilesByContainsFirstOrLastName(firstName, lastName))
                    .willReturn(expected);

            List<ProfileEntity> result = profileService.getProfilesByFirstNameAndLastName(firstName, lastName);

            assertThat(result).containsExactlyElementsOf(expected);
        }
    }

    @Nested
    @DisplayName("Проверка существования профиля по userTag")
    class ExistsByUserTag {

        @Test
        @DisplayName("Возвращает true, если профиль с таким userTag существует")
        void shouldReturnTrueWhenProfileExists() {
            String userTag = "john_doe";
            given(profileRepository.existsByUserTag(userTag)).willReturn(true);

            boolean result = profileService.existsByUserTag(userTag);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Возвращает false, если профиль с таким userTag не существует")
        void shouldReturnFalseWhenProfileDoesNotExist() {
            String userTag = "unknown_tag";
            given(profileRepository.existsByUserTag(userTag)).willReturn(false);

            boolean result = profileService.existsByUserTag(userTag);

            assertThat(result).isFalse();
        }
    }
}
