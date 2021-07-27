package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Category;
import br.com.challengebackend.aluraflixapi.models.Video;
import br.com.challengebackend.aluraflixapi.repository.CategoryRepository;
import br.com.challengebackend.aluraflixapi.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CategoryService categoryService;


    @Test
    void shouldFindAnVideoById() {
        var video = Video.builder().id(randomUUID()).build();
        Mockito.when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        var result = videoService.findVideoById(video.getId());

        assertThat(result.getId(), is(video.getId()));
        assertEquals(result, video);
    }

    @Test
    void shouldThrowExceptionWhenFindAnVideoById() {
        var video = Video.builder().id(randomUUID()).build();
        Mockito.when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        videoService.findVideoById(video.getId());

        try {
            videoService.findVideoById(randomUUID());
        } catch (ObjectNotFoundException e) {
            assertEquals("Object not found", e.getMessage());
        }

        assertThrows(ObjectNotFoundException.class, () -> videoService.findVideoById(randomUUID()));

    }

    @Test
    void shouldCreatedNewVideo() {
        var category = Category.builder().id(randomUUID()).build();
        var video = Video.builder().id(randomUUID()).category(category).build();
        Mockito.when(videoRepository.save(video)).thenReturn(video);
        Mockito.when(categoryService.findCategoryById(category.getId())).thenReturn(category);
        var result = videoService.createVideo(video, category.getId());

        assertThat(result.getId(), is(video.getId()));
    }

    @Test
    void shouldUpdateVideo() {
        var id = randomUUID();
        var video = Video.builder()
                .id(id)
                .description("teste")
                .build();

        var videoRequest = VideoRequest.builder()
                .description("Teste 2")
                .build();

        var newVideo = new Video(video, videoRequest);

        Mockito.when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        Mockito.when(videoRepository.save(newVideo)).thenReturn(newVideo);

        var result = videoService.updateVideo(id, videoRequest);

        assertEquals(result, newVideo);
        assertThat(result.getId(), is(id));
        assertThat(result.getDescription(), is(videoRequest.getDescription()));

    }
}
