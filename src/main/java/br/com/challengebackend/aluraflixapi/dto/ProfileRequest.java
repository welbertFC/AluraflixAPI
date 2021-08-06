package br.com.challengebackend.aluraflixapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileRequest {

    @NotBlank
    private String profileName;
}
