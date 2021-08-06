package br.com.challengebackend.aluraflixapi.mappers;

import br.com.challengebackend.aluraflixapi.dto.UserRequest;
import br.com.challengebackend.aluraflixapi.dto.UserResponse;
import br.com.challengebackend.aluraflixapi.models.UserClient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserMapper() {
    }

    private static final ModelMapper mapper = new ModelMapper();

    public static UserClient convertToModel(UserRequest request) {
        return mapper.map(request, UserClient.class);
    }

    public static UserResponse convertToResponse(UserClient user) {
        return mapper.map(user, UserResponse.class);
    }
}
