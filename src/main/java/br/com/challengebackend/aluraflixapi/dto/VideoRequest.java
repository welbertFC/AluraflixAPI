package br.com.challengebackend.aluraflixapi.dto;


import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VideoRequest {


    @NotBlank(message = "Campo não pode ser vazio")
    @NotNull(message = "Campo não pode ser nulo")
    private String title;

    @NotBlank(message = "Campo não pode ser vazio")
    @NotNull(message = "Campo não pode ser nulo")
    private String description;

    @URL
    private String url;
}
