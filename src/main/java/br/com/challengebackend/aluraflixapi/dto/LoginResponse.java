package br.com.challengebackend.aluraflixapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String Authorization;
    private String RefreshToken;

}
