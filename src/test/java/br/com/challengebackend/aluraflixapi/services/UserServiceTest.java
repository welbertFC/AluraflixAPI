package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.UserClient;
import br.com.challengebackend.aluraflixapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private ProfileService profileService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Captor
    ArgumentCaptor<UserClient> userCaptor;

    private UserClient user;

    @BeforeEach
    void setup() {
        this.user = UserClient.builder().id(UUID.randomUUID()).build();
    }

    @Test
    void shouldFindAnUserById() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        var result = service.findById(user.getId());

        assertThat(result).isEqualTo(user);
        verify(repository, times(1)).findById(user.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindUserById() {
        when(repository.findById(user.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ObjectNotFoundException.class,
                () -> service.findById(user.getId()));

        assertThat(exception).hasMessage("Object not found");
    }

    @Test
    void shouldFindAnUserByEmail() {
        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        var result = service.findByEmail(user.getEmail());

        assertThat(result).isEqualTo(user);
        verify(repository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void shouldCreateAnUser() {
        var newUser = UserClient.builder()
                .id(null)
                .email("teste@gmail.com")
                .password("123456789")
                .build();

        service.saveUser(newUser);

        verify(repository).save(userCaptor.capture());

        var result = userCaptor.getValue();

        assertThat(result.getId()).isNotNull();
    }

    @Test
    void shouldFindAnUserByUsername() {
        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        var result = service.loadUserByUsername(user.getEmail());

        assertThat(result.getUsername()).isEqualTo(user.getEmail());
        verify(repository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenFindUserByUsername() {
        when(repository.findByEmail(user.getEmail())).thenReturn(null);

        var exception = assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername(user.getEmail()));

        assertThat(exception).hasMessage("User not found");
    }

    @Test
    void shouldAddProfile() {
        var profileId = UUID.randomUUID();
        var userId = UUID.randomUUID();

        var user = UserClient.builder()
                .id(userId)
                .profiles(new ArrayList<>())
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        service.addProfile(userId, profileId);

        assertThat(user.getProfiles().size()).isEqualTo(1);

    }

}
