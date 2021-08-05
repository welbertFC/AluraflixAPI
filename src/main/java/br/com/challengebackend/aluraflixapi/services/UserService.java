package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Profile;
import br.com.challengebackend.aluraflixapi.models.UserClient;
import br.com.challengebackend.aluraflixapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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
        user.generateId();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public UserClient findById(UUID idUser) {
        return repository.findById(idUser)
                .orElseThrow(ObjectNotFoundException::new);
    }

    public void addProfile(UUID idUser, UUID idProfile) {
        var user = findById(idUser);
        var profile = profileService.findProfileById(idProfile);
        user.getProfiles().add(profile);
        repository.save(user);
    }

}
