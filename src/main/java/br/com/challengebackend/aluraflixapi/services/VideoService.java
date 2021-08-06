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

    private final VideoRepository videoRepository;
    private final CategoryService categoryService;

    @Autowired
    public VideoService(VideoRepository videoRepository, CategoryService categoryService) {
        this.videoRepository = videoRepository;
        this.categoryService = categoryService;
    }

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
                ObjectNotFoundException::new);
    }

    public Video updateVideo(UUID videoId, Video videoRequest) {
        var video = findVideoById(videoId);
        video.update(videoRequest);
        return videoRepository.save(video);
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

    public Page<Video> findFirst5Video(Pageable pageable) {
        return videoRepository.queryFirst5ByOrderByCreateDateTime(pageable);
    }
}
