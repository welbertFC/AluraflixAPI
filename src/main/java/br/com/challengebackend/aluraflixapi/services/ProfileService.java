package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ArgumentNotValidException;
import br.com.challengebackend.aluraflixapi.exception.ObjectAlreadyCreatedException;
import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Profile;
import br.com.challengebackend.aluraflixapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public Profile saveProfile(Profile profile) {
        validationProfile(profile);
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

    public Profile findProfileByProfileName(String profileName) {
        return repository.findByProfileName(profileName);
    }

    private void validationProfile(Profile profile) {
        if (nonNull(findProfileByProfileName(profile.getProfileName()))) {

            throw new ObjectAlreadyCreatedException("Perfil já criado. Tente atualizá-lo");
        }
        if (!profile.getProfileName().startsWith("ROLE_")) {
            throw new ArgumentNotValidException("profile deve iniciar com ROLE_");
        }
    }

}
