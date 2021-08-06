package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Category;
import br.com.challengebackend.aluraflixapi.models.Video;
import br.com.challengebackend.aluraflixapi.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CategoryService categoryService;

    private Video video;

    @BeforeEach
    void setup() {
        var category = Category.builder().id(randomUUID()).build();
        this.video = Video.builder().id(randomUUID()).category(category).build();
    }


    @Test
    void shouldFindAnVideoById() {
        when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        var result = videoService.findVideoById(video.getId());

        assertThat(result.getId(), is(video.getId()));
        assertEquals(result, video);
    }

    @Test
    void shouldThrowExceptionWhenFindAnVideoById() {
        when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
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
        when(videoRepository.save(video)).thenReturn(video);
        when(categoryService.findCategoryById(video.getCategory().getId())).thenReturn(video.getCategory());
        var result = videoService.createVideo(video, video.getCategory().getId());

        assertThat(result.getId(), is(video.getId()));
    }

    @Test
    void shouldCreatedNewVideoWithCategoryIdNull() {
        var category = Category.builder().id(UUID.fromString("8ad8cc39-9feb-4817-a004-5a40d5efed51")).build();

        when(videoRepository.save(video)).thenReturn(video);
        when(categoryService.findCategoryById(category.getId())).thenReturn(category);

        var result = videoService.createVideo(video, null);

        assertThat(result.getCategory().getId(), is(category.getId()));
        assertThat(result.getId(), is(video.getId()));
    }

    @Test
    void shouldUpdateVideo() {
        var videoPersisted = Video.builder()
                .id(UUID.randomUUID())
                .title("teste")
                .description("teste")
                .url("teste")
                .category(Category.builder().build())
                .build();

        var video = Video.builder()
                .id(UUID.randomUUID())
                .title("teste 2")
                .description("teste 2")
                .url("teste 2")
                .category(Category.builder().build())
                .build();

        var expected = Video.builder()
                .id(videoPersisted.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .url(video.getUrl())
                .category(videoPersisted.getCategory())
                .build();

        when(videoRepository.findById(videoPersisted.getId())).thenReturn(Optional.of(videoPersisted));

        videoService.updateVideo(videoPersisted.getId(), video);

        verify(videoRepository, times(1)).save(expected);
    }

    @Test
    void shouldAllVideo() {
        final Pageable pageable = PageRequest.of(20, 20);

        when(videoRepository.findAll(pageable)).thenReturn(Page.empty());
        videoService.findAllVideos(pageable);

        verify(videoRepository, times(1))
                .findAll(pageable);
    }

    @Test
    void shouldAllVideosByCategoryId() {
        final Pageable pageable = PageRequest.of(20, 20);

        when(videoRepository.findAllByCategoryId(video.getCategory().getId(),
                pageable)).thenReturn(Page.empty());
        videoService.findAllVideoByCategory(video.getCategory().getId(), pageable);

        verify(videoRepository, times(1))
                .findAllByCategoryId(video.getCategory().getId(), pageable);
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

        when(videoRepository.findVideoByTitleContains("test", pageable)).thenReturn(listPage);
        var result = videoService.findAllVideosByTitle("test", pageable);

        assertThat(result.getSize(), is(2));
        assertEquals(result.iterator().next().getTitle(), "test");
    }

    @Test
    void shouldDeleteVideo() {
        when(videoRepository.findById(video.getId())).thenReturn(Optional.of(video));
        doNothing().when(videoRepository).delete(video);

        videoService.deleteVideo(video.getId());

        verify(videoRepository, Mockito.times(1)).delete(video);
    }

    @Test
    void shouldFindTop5Videos() {
        final var pageable = PageRequest.of(20, 20);

        when(videoRepository.queryFirst5ByOrderByCreateDateTime(pageable)).thenReturn(Page.empty());

        videoService.findFirst5Video(pageable);

        verify(videoRepository, times(1))
                .queryFirst5ByOrderByCreateDateTime(pageable);
    }
}
