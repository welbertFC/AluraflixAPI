package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import br.com.challengebackend.aluraflixapi.dto.VideoResponse;
import br.com.challengebackend.aluraflixapi.mappers.VideoMapper;
import br.com.challengebackend.aluraflixapi.services.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
@Api(tags = "Videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ApiOperation(value = "Insert new user")
    public ResponseEntity<VideoResponse> insert(@Valid @RequestBody VideoRequest videoRequest) {
        var video = videoService.createVideo(VideoMapper.convertToModel(videoRequest), videoRequest.getCategory());
        var videoResponse = VideoMapper.convertToResponse(video);
        videoResponse.setCategory(video.getCategory().getId());
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{Id}")
                        .buildAndExpand(videoResponse.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(videoResponse);
    }

    @GetMapping
    @ApiOperation(value = "Find all user")
    public ResponseEntity<Page<VideoResponse>> listAll(
            @RequestParam(value = "title", defaultValue = "") String title, Pageable pageable) {
        if (title == null || title.isEmpty()) {
            return ResponseEntity.ok(videoService.findAllVideos(pageable)
                    .map(video -> {
                        var videoResponse = VideoMapper.convertToResponse(video);
                        videoResponse.setCategory(video.getCategory().getId());
                        return videoResponse;
                    }));
        } else {
            return ResponseEntity.ok(videoService.findAllVideosByTitle(title, pageable)
                    .map(video -> {
                        var videoResponse = VideoMapper.convertToResponse(video);
                        videoResponse.setCategory(video.getCategory().getId());
                        return videoResponse;
                    }));
        }

    }

    @GetMapping("/{videoId}")
    @ApiOperation(value = "Find user by ID")
    public ResponseEntity<VideoResponse> findById(@PathVariable UUID videoId) {
        var video = videoService.findVideoById(videoId);
        var videoResponse = VideoMapper.convertToResponse(video);
        videoResponse.setCategory(video.getCategory().getId());
        return ResponseEntity.ok(videoResponse);
    }

    @GetMapping("/free")
    @ApiOperation(value = "Find top 10 videos")
    public ResponseEntity<Page<VideoResponse>> findFirst5(Pageable pageable) {
        var video = videoService.findFirst5Video(pageable);
        return ResponseEntity.ok(video.map(VideoMapper::convertToResponse));
    }

    @PutMapping("/{videoId}")
    @ApiOperation(value = "Update video")
    public ResponseEntity<VideoResponse> update(
            @PathVariable UUID videoId,
            @Valid @RequestBody VideoRequest videoRequest) {
        var video = videoService.updateVideo(videoId, VideoMapper.convertToModel(videoRequest));
        var videoResponse = VideoMapper.convertToResponse(video);
        videoResponse.setCategory(video.getCategory().getId());
        return ResponseEntity.ok(videoResponse);
    }

    @DeleteMapping("/{videoId}")
    @ApiOperation(value = "Delete video")
    public ResponseEntity<Void> delete(@PathVariable UUID videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok().build();
    }

}
