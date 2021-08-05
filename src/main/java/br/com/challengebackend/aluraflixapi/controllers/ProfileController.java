package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.ProfileRequest;
import br.com.challengebackend.aluraflixapi.dto.ProfileResponse;
import br.com.challengebackend.aluraflixapi.mappers.ProfileMapper;
import br.com.challengebackend.aluraflixapi.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ProfileResponse> insert(@Valid @RequestBody ProfileRequest request) {
        var profile = service.saveProfile(ProfileMapper.convertToModel(request));
        var profileResponse = ProfileMapper.convertToResponse(profile);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{Id}")
                        .buildAndExpand(profileResponse.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(profileResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProfileResponse>> findAll(Pageable pageable) {
        var profile = service.findAllProfile(pageable);
        var profileResponse = profile.map(ProfileMapper::convertToResponse);
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/{idProfile}")
    public ResponseEntity<ProfileResponse> findById(@PathVariable UUID idProfile) {
        var profile = service.findProfileById(idProfile);
        return ResponseEntity.ok(ProfileMapper.convertToResponse(profile));
    }
}
