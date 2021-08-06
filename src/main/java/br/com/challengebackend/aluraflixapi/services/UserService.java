package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ArgumentNotValidException;
import br.com.challengebackend.aluraflixapi.exception.ObjectAlreadyCreatedException;
import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.UserClient;
import br.com.challengebackend.aluraflixapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserClient(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getProfiles());
    }

    public UserClient saveUser(UserClient user) {
        validationUser(user);
        user.generateId();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public UserClient findById(UUID idUser) {
        return repository.findById(idUser)
                .orElseThrow(ObjectNotFoundException::new);
    }

    public UserClient findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void addProfile(UUID idUser, UUID idProfile) {
        var user = findById(idUser);
        var profile = profileService.findProfileById(idProfile);
        user.getProfiles().add(profile);
        repository.save(user);
    }

    private void validationUser(UserClient user) {
        if (nonNull(findByEmail(user.getEmail()))) {
            throw new ObjectAlreadyCreatedException("Usuario já criado. Tente atualizá-lo");
        }
        if (user.getPassword().length() < 8) {
            throw new ArgumentNotValidException("Senha deve possuir mais de 8 caracteres");
        }
    }

}
