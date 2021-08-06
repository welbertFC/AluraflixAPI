package br.com.challengebackend.aluraflixapi.mappers;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import br.com.challengebackend.aluraflixapi.dto.VideoResponse;
import br.com.challengebackend.aluraflixapi.models.Video;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    private VideoMapper() {
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Video convertToModel(VideoRequest videoRequest) {
        return modelMapper.map(videoRequest, Video.class);
    }

    public static VideoResponse convertToResponse(Video video) {
        return modelMapper.map(video, VideoResponse.class);
    }

}
