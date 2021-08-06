package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.CategoryRequest;
import br.com.challengebackend.aluraflixapi.dto.CategoryResponse;
import br.com.challengebackend.aluraflixapi.mappers.CategoryMapper;
import br.com.challengebackend.aluraflixapi.mappers.VideoMapper;
import br.com.challengebackend.aluraflixapi.models.Category;
import br.com.challengebackend.aluraflixapi.services.CategoryService;
import br.com.challengebackend.aluraflixapi.services.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @MockBean
    private VideoService videoService;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private VideoMapper videoMapper;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnCreatedWhenPostValidRequest() throws Exception {
        var categoryRequest = CategoryRequest.builder()
                .title("teste")
                .color("teste")
                .build();

        var category = Category.builder()
                .title(categoryRequest.getTitle())
                .color(categoryRequest.getColor())
                .build();

        var categoryReturned = Category.builder()
                .id(UUID.randomUUID())
                .title(category.getTitle())
                .color(category.getColor())
                .build();

        var categoryResponseExpected = CategoryResponse.builder()
                .id(categoryReturned.getId())
                .title(categoryReturned.getTitle())
                .color(categoryReturned.getColor())
                .build();


        when(service.createCategory(category)).thenReturn(categoryReturned);

        var json = mapper.writeValueAsString(categoryRequest);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(this.mapper.writeValueAsString(categoryResponseExpected)));
    }

    @Test
    void shouldReturnOkWhenGetByValidId() throws Exception {
        var idCategory = UUID.randomUUID();
        var category = Category.builder()
                .id(idCategory)
                .build();

        var categoryResult = CategoryResponse.builder()
                .id(idCategory)
                .build();

        when(service.findCategoryById(idCategory)).thenReturn(category);

        var json = mapper.writeValueAsString(categoryResult);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categorias/{idCategory}", idCategory)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(json))
                .andExpect(status().isOk());
    }

}

