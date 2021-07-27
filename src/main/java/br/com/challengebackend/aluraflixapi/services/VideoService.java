package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
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

    @Autowired
    private CategoryService categoryService;

    public Video createVideo(Video video, UUID idCategory) {
        if (idCategory == null) {
            video.setCategory(categoryService.findCategoryById(
                    UUID.fromString("8ad8cc39-9feb-4817-a004-5a40d5efed51")));
        } else {
            video.setCategory(categoryService.findCategoryById(idCategory));
        }
        video.generateId();
        return videoRepository.save(video);
    }

    public Page<Video> findAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    public Video findVideoById(UUID videoId) {
        return videoRepository.findById(videoId).orElseThrow(
                () -> new ObjectNotFoundException());
    }

    public Video updateVideo(UUID videoId, VideoRequest videoRequest) {
        var video = findVideoById(videoId);
        return videoRepository.save(
                new Video(video, videoRequest));
    }

    public void deleteVideo(UUID videoId) {
        var video = findVideoById(videoId);
        videoRepository.delete(video);
    }

    public Page<Video> findAllVideoByCategory(UUID IdCategory, Pageable pageable) {
        return videoRepository.findAllByCategoryId(IdCategory, pageable);
    }

    public Page<Video> findAllVideosByTitle(String title, Pageable pageable) {
        return videoRepository.findVideoByTitleContains(title, pageable);
    }
}
