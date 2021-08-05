package br.com.challengebackend.aluraflixapi.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String senha;
}
