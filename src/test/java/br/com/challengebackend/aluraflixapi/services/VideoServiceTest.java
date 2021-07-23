package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Video;
import br.com.challengebackend.aluraflixapi.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @Test
    void shouldFindAnVideoById() {
        var video = Video.builder().id(randomUUID()).build();
        Mockito.when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        var result = videoService.findVideoById(video.getId());

        assertThat(result.getId(), is(video.getId()));
    }

    @Test
    void shouldThrowExceptionWhenFindAnVideoById() {
        var video = Video.builder().id(randomUUID()).build();
        Mockito.when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        var result = videoService.findVideoById(video.getId());

        assertThrows(ObjectNotFoundException.class,() -> videoService.findVideoById(randomUUID()));

    }
}
