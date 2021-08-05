package br.com.challengebackend.aluraflixapi.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String Authorization;
    private String RefreshToken;

}
