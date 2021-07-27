package br.com.challengebackend.aluraflixapi.services;

import br.com.challengebackend.aluraflixapi.dto.CategoryRequest;
import br.com.challengebackend.aluraflixapi.exception.ObjectNotFoundException;
import br.com.challengebackend.aluraflixapi.models.Category;
import br.com.challengebackend.aluraflixapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        category.generateId();
        return categoryRepository.save(category);
    }

    public Page<Category> findAllCategory(Pageable pageable){

        return categoryRepository.findAll(pageable);
    }

    public Category findCategoryById(UUID idCategory){
        return categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ObjectNotFoundException());
    }

    public Category updateCategory(UUID idCategory, CategoryRequest categoryRequest){
        var category = findCategoryById(idCategory);
        return categoryRepository.save(
                new Category(category, categoryRequest));
    }

    public void deleteCategory(UUID idCategory){
        var category = findCategoryById(idCategory);
        categoryRepository.delete(category);
    }

}
