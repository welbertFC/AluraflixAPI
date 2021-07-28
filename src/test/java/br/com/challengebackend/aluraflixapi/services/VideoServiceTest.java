package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Category;
import br.com.challengebackend.aluraflixapi.models.Video;
import br.com.challengebackend.aluraflixapi.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    void shouldCreatedNewVideoWithCategoryIdNull() {
        var category = Category.builder().id(UUID.fromString("8ad8cc39-9feb-4817-a004-5a40d5efed51")).build();
        var video = Video.builder().id(randomUUID()).build();

        Mockito.when(videoRepository.save(video)).thenReturn(video);
        Mockito.when(categoryService.findCategoryById(category.getId())).thenReturn(category);


        var result = videoService.createVideo(video, null);

        assertThat(result.getCategory().getId(), is(category.getId()));
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

    @Test
    void shouldAllVideo() {
        final Pageable pageable = PageRequest.of(20, 20);
        var video = Video.builder().id(randomUUID()).build();
        var video2 = Video.builder().id(randomUUID()).build();
        var video3 = Video.builder().id(randomUUID()).build();
        var listVideo = new ArrayList<Video>();
        listVideo.add(video);
        listVideo.add(video2);
        listVideo.add(video3);
        var listPage = new PageImpl<>(listVideo);

        Mockito.when(videoRepository.findAll(pageable)).thenReturn(listPage);
        var result = videoService.findAllVideos(pageable);

        assertThat(result.getSize(), is(3));
    }

    @Test
    void shouldAllVideosByCategoryId() {
        final Pageable pageable = PageRequest.of(20, 20);
        var id = randomUUID();
        var category = Category.builder().id(id).build();
        var video = Video.builder().id(randomUUID()).category(category).build();
        var video1 = Video.builder().id(randomUUID()).category(category).build();
        var video2 = Video.builder().id(randomUUID()).category(category).build();

        var listVideo = new ArrayList<Video>();
        listVideo.add(video);
        listVideo.add(video1);
        listVideo.add(video2);

        var listPage = new PageImpl<>(listVideo);

        Mockito.when(videoRepository.findAllByCategoryId(id, pageable)).thenReturn(listPage);
        var result = videoService.findAllVideoByCategory(id, pageable);

        assertThat(result.getSize(), is(3));

    }

    @Test
    void shouldAllVideosByTitleOfVideo() {
        final Pageable pageable = PageRequest.of(20, 20);
        var video = Video.builder().id(randomUUID()).title("test").build();
        var video1 = Video.builder().id(randomUUID()).title("test").build();

        List<Video> listVideo = new ArrayList<>();
        listVideo.add(video);
        listVideo.add(video1);


        var listPage = new PageImpl<>(listVideo);

        Mockito.when(videoRepository.findVideoByTitleContains("test", pageable)).thenReturn(listPage);
        var result = videoService.findAllVideosByTitle("test", pageable);

        assertThat(result.getSize(), is(2));
        assertEquals(result.iterator().next().getTitle(), "test");

    }

    @Test
    void shouldDeleteVideo() {
        var id = randomUUID();
        var video = Video.builder().id(id).build();

        Mockito.when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        Mockito.doNothing().when(videoRepository).delete(video);

        videoService.deleteVideo(id);

        Mockito.verify(videoRepository, Mockito.times(1)).delete(video);


    }


}
