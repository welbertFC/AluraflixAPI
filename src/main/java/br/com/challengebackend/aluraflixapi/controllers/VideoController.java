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

import javax.validation.Valid;

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
        return ResponseEntity.ok(videoResponse);
    }

    @GetMapping
    public ResponseEntity<Page<VideoResponse>> findAll(Pageable pageable) {
        var videos = videoService.findAllVideos(pageable);
        var videoResponse = videos.map(obj -> videoMapper.convertToResponse(obj));
        return ResponseEntity.ok(videoResponse);
    }
}
