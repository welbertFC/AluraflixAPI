package br.com.challengebackend.aluraflixapi.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
public class VideoRequest {


    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private UUID category;

    @URL
    private String url;
}
