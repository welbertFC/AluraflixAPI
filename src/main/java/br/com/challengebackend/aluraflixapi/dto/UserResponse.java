package br.com.challengebackend.aluraflixapi.dto;

import br.com.challengebackend.aluraflixapi.models.Profile;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Profile> profileCollections;
}
