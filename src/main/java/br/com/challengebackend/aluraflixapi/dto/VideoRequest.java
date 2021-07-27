package br.com.challengebackend.aluraflixapi.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class VideoRequest {


    @NotBlank(message = "Campo não pode ser vazio")
    @NotNull(message = "Campo não pode ser nulo")
    private String title;

    @NotBlank(message = "Campo não pode ser vazio")
    @NotNull(message = "Campo não pode ser nulo")
    private String description;

    private UUID category;

    @URL
    private String url;
}
