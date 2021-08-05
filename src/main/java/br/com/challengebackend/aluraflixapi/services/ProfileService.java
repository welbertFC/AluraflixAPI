package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Profile;
import br.com.challengebackend.aluraflixapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public Profile saveProfile(Profile profile) {
        profile.generateId();
        return repository.save(profile);
    }

    public Page<Profile> findAllProfile(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Profile findProfileById(UUID profileID) {
        return repository.findById(profileID)
                .orElseThrow(ObjectNotFoundException::new);
    }

}
