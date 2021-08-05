package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.CategoryRequest;
import br.com.challengebackend.aluraflixapi.dto.CategoryResponse;
import br.com.challengebackend.aluraflixapi.dto.VideoResponse;
import br.com.challengebackend.aluraflixapi.mappers.CategoryMapper;
import br.com.challengebackend.aluraflixapi.mappers.VideoMapper;
import br.com.challengebackend.aluraflixapi.services.CategoryService;
import br.com.challengebackend.aluraflixapi.services.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Category")
public class CategoryController {

    private final CategoryService categoryService;
    private final VideoService videoService;

    @Autowired
    public CategoryController(CategoryService categoryService, VideoService videoService) {
        this.categoryService = categoryService;
        this.videoService = videoService;
    }

    @PostMapping
    @ApiOperation(value = "Insert new category")
    public ResponseEntity<CategoryResponse> insert(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        var category = categoryService.createCategory(CategoryMapper.convertToModel(categoryRequest));
        var categoryResponse = CategoryMapper.convertToResponse(category);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{Id}")
                        .buildAndExpand(categoryResponse.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(categoryResponse);
    }

    @GetMapping
    @ApiOperation(value = "Find All category")
    public ResponseEntity<Page<CategoryResponse>> findAll(Pageable pageable) {
        var category = categoryService.findAllCategory(pageable);
        var categoryResponse = category.map(CategoryMapper::convertToResponse);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/{idCategory}")
    @ApiOperation(value = "Find category by Id")
    public ResponseEntity<CategoryResponse> findById(
            @PathVariable UUID idCategory) {
        var category = categoryService.findCategoryById(idCategory);
        var categoryResponse = CategoryMapper.convertToResponse(category);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{idCategory}")
    @ApiOperation(value = "Update category by Id")
    public ResponseEntity<CategoryResponse> update(@Valid @RequestBody CategoryRequest categoryRequest,
                                                   @PathVariable UUID idCategory) {
        var category = categoryService.updateCategory(idCategory, CategoryMapper.convertToModel(categoryRequest));
        var categoryResponse = CategoryMapper.convertToResponse(category);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{idCategory}")
    @ApiOperation(value = "Delete category by Id")
    public ResponseEntity<Void> delete(@PathVariable UUID idCategory) {
        categoryService.deleteCategory(idCategory);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idCategory}/videos")
    @ApiOperation(value = "Find all video by category Id")
    public ResponseEntity<Page<VideoResponse>> listAll(
            @PathVariable UUID idCategory, Pageable pageable) {
        var videos = videoService.findAllVideoByCategory(idCategory, pageable);
        var videoResponse = videos.map(VideoMapper::convertToResponse);
        return ResponseEntity.ok(videoResponse);

    }
}
