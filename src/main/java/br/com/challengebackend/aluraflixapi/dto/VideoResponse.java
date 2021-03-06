package br.com.challengebackend.aluraflixapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VideoResponse {

    private UUID id;
    private String title;
    private String description;
    private String url;
    private UUID category;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createDateTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime modifiedDate;
}
