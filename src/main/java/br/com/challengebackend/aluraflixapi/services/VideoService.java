package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Video;
import br.com.challengebackend.aluraflixapi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video createVideo(Video video){
        video.generateId();
        return videoRepository.save(video);
    }

    public Page<Video> findAllVideos(Pageable pageable){
        return videoRepository.findAll(pageable);
    }

    public Video findVideoById(UUID id){
        return videoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException());
    }
}
