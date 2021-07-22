package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import br.com.challengebackend.aluraflixapi.dto.VideoResponse;
import br.com.challengebackend.aluraflixapi.mappers.VideoMapper;
import br.com.challengebackend.aluraflixapi.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoMapper videoMapper;

    @PostMapping
    public ResponseEntity<VideoResponse> create(@Valid @RequestBody VideoRequest videoRequest) {
        var video = videoService.createVideo(videoMapper.convertToModel(videoRequest));
        var videoResponse = videoMapper.convertToResponse(video);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{Id}")
                        .buildAndExpand(videoResponse.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(videoResponse);
    }

    @GetMapping
    public ResponseEntity<Page<VideoResponse>> findAll(Pageable pageable) {
        var videos = videoService.findAllVideos(pageable);
        var videoResponse = videos.map(obj -> videoMapper.convertToResponse(obj));
        return ResponseEntity.ok(videoResponse);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponse> findById(@PathVariable UUID videoId) {
        var video = videoService.findVideoById(videoId);
        var videoResponse = videoMapper.convertToResponse(video);
        return ResponseEntity.ok(videoResponse);
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable UUID videoId, @Valid @RequestBody VideoRequest videoRequest) {
        var video = videoService.updateVideo(videoId, videoRequest);
        var videoResponse = videoMapper.convertToResponse(video);
        return ResponseEntity.ok(videoResponse);
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok().build();
    }
}
