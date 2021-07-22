package br.com.challengebackend.aluraflixapi.repository;

import br.com.challengebackend.aluraflixapi.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
}
