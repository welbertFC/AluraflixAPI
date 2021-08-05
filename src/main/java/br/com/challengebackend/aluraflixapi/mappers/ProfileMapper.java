package br.com.challengebackend.aluraflixapi.mappers;

import br.com.challengebackend.aluraflixapi.dto.ProfileRequest;
import br.com.challengebackend.aluraflixapi.dto.ProfileResponse;
import br.com.challengebackend.aluraflixapi.models.Profile;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProfileMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Profile convertToModel(ProfileRequest request) {
        return mapper.map(request, Profile.class);
    }

    public static ProfileResponse convertToResponse(Profile profile) {
        return mapper.map(profile, ProfileResponse.class);
    }
}
