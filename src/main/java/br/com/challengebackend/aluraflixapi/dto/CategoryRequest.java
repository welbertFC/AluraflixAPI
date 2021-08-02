package br.com.challengebackend.aluraflixapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String color;
}
