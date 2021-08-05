package br.com.challengebackend.aluraflixapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProfileResponse {

    private UUID id;
    private String profile;
}
