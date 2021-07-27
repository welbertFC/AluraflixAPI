package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.CategoryRequest;
import br.com.challengebackend.aluraflixapi.dto.CategoryResponse;
import br.com.challengebackend.aluraflixapi.dto.VideoResponse;
import br.com.challengebackend.aluraflixapi.mappers.CategoryMapper;
import br.com.challengebackend.aluraflixapi.mappers.VideoMapper;
import br.com.challengebackend.aluraflixapi.services.CategoryService;
import br.com.challengebackend.aluraflixapi.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VideoMapper videoMapper;

    @PostMapping
    public ResponseEntity<CategoryResponse> insertCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        var category = categoryService.createCategory(categoryMapper.convertToModel(categoryRequest));
        var categoryResponse = categoryMapper.convertToResponse(category);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{Id}")
                        .buildAndExpand(categoryResponse.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(categoryResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAll(Pageable pageable) {
        var category = categoryService.findAllCategory(pageable);
        var categoryResponse = category.map(obj -> categoryMapper.convertToResponse(obj));
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<CategoryResponse> findById(
            @PathVariable UUID idCategory) {
        var category = categoryService.findCategoryById(idCategory);
        var categoryResponse = categoryMapper.convertToResponse(category);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{idCategory}")
    public ResponseEntity<CategoryResponse> update(@Valid @RequestBody CategoryRequest categoryRequest,
                                                   @PathVariable UUID idCategory) {
        var category = categoryService.updateCategory(idCategory, categoryRequest);
        var categoryResponse = categoryMapper.convertToResponse(category);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{idCategory}")
    public ResponseEntity<Void> delete(@PathVariable UUID idCategory) {
        categoryService.deleteCategory(idCategory);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idCategory}/videos")
    public ResponseEntity<Page<VideoResponse>> findAllByCategory(
            @PathVariable UUID idCategory, Pageable pageable) {
        var videos = videoService.findAllVideoByCategory(idCategory, pageable);
        var videoResponse = videos.map(obj -> videoMapper.convertToResponse(obj));
        return ResponseEntity.ok(videoResponse);

    }
}
