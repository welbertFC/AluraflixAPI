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
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    public Profile findProfileByProfileName(String profile){
        return repository.findByProfile(profile);
    }

    private void validationProfile(Profile profile) {
        if (nonNull(findProfileByProfileName(profile.getProfile()))){

            throw new ObjectAlreadyCreatedException("Perfil já criado. Tente atualizá-lo");
        }
        if (!profile.getProfile().startsWith("ROLE_")){
            throw new ArgumentNotValidException("profile deve iniciar com ROLE_");
        }
    }

}
