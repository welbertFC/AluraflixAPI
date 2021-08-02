package br.com.challengebackend.aluraflixapi.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void shouldUpdate(){
        var persistedCategory = Category.builder()
                .id(UUID.randomUUID())
                .title("teste")
                .color("teste")
                .build();

        var category = Category.builder()
                .id(UUID.randomUUID())
                .title("teste 2")
                .color("teste 2")
                .build();

        var excepted = Category.builder()
                .id(persistedCategory.getId())
                .title(category.getTitle())
                .color(category.getColor())
                .build();

        persistedCategory.update(category);
        assertThat(persistedCategory).isEqualTo(excepted);
    }

    @Test
    void shouldGeneratedId(){
        var category = Category.builder().build();

        category.generateId();

        assertThat(category.getId()).isNotNull();
    }
}
