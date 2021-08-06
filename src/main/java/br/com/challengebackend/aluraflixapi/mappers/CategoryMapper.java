package br.com.challengebackend.aluraflixapi.mappers;

import br.com.challengebackend.aluraflixapi.dto.CategoryRequest;
import br.com.challengebackend.aluraflixapi.dto.CategoryResponse;
import br.com.challengebackend.aluraflixapi.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private CategoryMapper() {
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Category convertToModel(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }

    public static CategoryResponse convertToResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }
}
