package br.com.challengebackend.aluraflixapi.repository;

import br.com.challengebackend.aluraflixapi.models.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserClient, UUID> {

    UserClient findByEmail(String email);
}
