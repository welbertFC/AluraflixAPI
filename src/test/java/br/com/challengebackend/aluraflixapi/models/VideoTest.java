package br.com.challengebackend.aluraflixapi.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VideoTest {

    @Test
    void shouldUpdate() {
        var video1 = Video.builder()
                .id(UUID.randomUUID())
                .title("teste")
                .description("teste")
                .url("teste")
                .category(Category.builder().build())
                .build();

        var video2 = Video.builder()
                .id(UUID.randomUUID())
                .title("teste 2")
                .description("teste 2")
                .url("teste 2")
                .category(Category.builder().build())
                .build();

        var expected = Video.builder()
                .id(video1.getId())
                .title(video2.getTitle())
                .description(video2.getDescription())
                .url(video2.getUrl())
                .category(video1.getCategory())
                .build();

        video1.update(video2);

        assertThat(video1).isEqualTo(expected);
    }

    @Test
    void shouldGeneratedId() {
        var video = Video.builder().id(null).build();

        video.generateId();

        assertThat(video.getId()).isNotNull();
    }
}
