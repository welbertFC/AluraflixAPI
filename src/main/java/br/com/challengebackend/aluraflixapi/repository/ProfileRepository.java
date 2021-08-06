package br.com.challengebackend.aluraflixapi.repository;

import br.com.challengebackend.aluraflixapi.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Profile findByProfile(String profile);
}