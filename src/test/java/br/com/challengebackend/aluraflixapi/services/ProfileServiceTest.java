package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Profile;
import br.com.challengebackend.aluraflixapi.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService service;

    @Mock
    private ProfileRepository repository;

    @Captor
    ArgumentCaptor<Profile> profileCaptor;

    private Profile profile;

    @BeforeEach
    void setup() {
        this.profile = Profile.builder().id(UUID.randomUUID()).build();
    }

    @Test
    void shouldFindAnProfileById() {
        when(repository.findById(profile.getId())).thenReturn(Optional.of(profile));

        var result = service.findProfileById(profile.getId());

        assertThat(result).isEqualTo(profile);
        verify(repository, times(1)).findById(profile.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindProfileById() {
        when(repository.findById(profile.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ObjectNotFoundException.class,
                () -> service.findProfileById(profile.getId()));

        assertThat(exception).hasMessage("Object not found");
    }

    @Test
    void shouldFindAnProfileByProfileName() {
        when(repository.findByProfile(profile.getProfileName())).thenReturn(profile);

        var result = service.findProfileByProfileName(profile.getProfileName());

        assertThat(result).isEqualTo(profile);
        verify(repository, times(1)).findByProfile(profile.getProfileName());
    }

    @Test
    void shouldAllProfile() {
        final var pageable = PageRequest.of(20, 20);

        when(repository.findAll(pageable)).thenReturn(Page.empty());

        service.findAllProfile(pageable);

        verify(repository, times(1))
                .findAll(pageable);
    }

    @Test
    void shouldCreateAnProfile() {
        var newProfile = Profile.builder()
                .id(null)
                .profileName("ROLE_TEST")
                .build();

        service.saveProfile(newProfile);

        verify(repository).save(profileCaptor.capture());

        var result = profileCaptor.getValue();

        assertThat(result.getId()).isNotNull();
    }



}
