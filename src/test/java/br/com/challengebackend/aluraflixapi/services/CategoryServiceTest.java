package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Category;
import br.com.challengebackend.aluraflixapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Captor
    ArgumentCaptor<Category> categoryCaptor;

    private Category category;

    @BeforeEach
    void setup() {
        this.category = Category.builder().id(UUID.randomUUID()).build();
    }

    @Test
    void shouldFindAnCategoryById() {
        when(repository.findById(category.getId())).thenReturn(Optional.of(category));

        var result = service.findCategoryById(category.getId());

        assertThat(result).isEqualTo(category);
        verify(repository, times(1)).findById(category.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindCategoryById() {
        when(repository.findById(category.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ObjectNotFoundException.class,
                () -> service.findCategoryById(category.getId()));

        assertThat(exception).hasMessage("Object not found");
    }

    @Test
    void shouldCreateAnCategory() {
        var newCategory = Category.builder()
                .id(null)
                .build();

        service.createCategory(newCategory);

        verify(repository).save(categoryCaptor.capture());

        var result = categoryCaptor.getValue();

        assertThat(result.getId()).isNotNull();
    }

    @Test
    void shouldAllCategory() {
        final var pageable = PageRequest.of(20, 20);

        when(repository.findAll(pageable)).thenReturn(Page.empty());

        service.findAllCategory(pageable);

        verify(repository, times(1))
                .findAll(pageable);
    }

    @Test
    void shouldUpdateCategoryById() {
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

        when(repository.findById(persistedCategory.getId())).thenReturn(Optional.of(persistedCategory));

        service.updateCategory(persistedCategory.getId(), category);

        verify(repository, times(1)).save(excepted);
    }

    @Test
    void shouldDeleteAnCategoryById(){
        when(repository.findById(category.getId())).thenReturn(Optional.of(category));

        service.deleteCategory(category.getId());

        verify(repository, times(1)).delete(category);

    }
}
