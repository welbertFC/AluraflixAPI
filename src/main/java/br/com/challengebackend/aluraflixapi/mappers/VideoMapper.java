package br.com.challengebackend.aluraflixapi.mappers;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import br.com.challengebackend.aluraflixapi.dto.VideoResponse;
import br.com.challengebackend.aluraflixapi.models.Video;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Video convertToModel(VideoRequest videoRequest) {
        return modelMapper.map(videoRequest, Video.class);
    }

    public VideoResponse convertToResponse(Video video) {
        return modelMapper.map(video, VideoResponse.class);
    }

}
