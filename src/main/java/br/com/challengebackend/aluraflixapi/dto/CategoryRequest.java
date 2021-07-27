package br.com.challengebackend.aluraflixapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryRequest {

    @NotBlank(message = "Campo não pode ser vazio")
    @NotNull(message = "Campo não pode ser nulo")
    private String title;

    @NotBlank(message = "Campo não pode ser vazio")
    @NotNull(message = "Campo não pode ser nulo")
    private String color;
}
