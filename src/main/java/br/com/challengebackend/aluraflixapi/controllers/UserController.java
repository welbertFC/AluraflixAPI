package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.UserRequest;
import br.com.challengebackend.aluraflixapi.dto.UserResponse;
import br.com.challengebackend.aluraflixapi.mappers.UserMapper;
import br.com.challengebackend.aluraflixapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> insert(@Valid @RequestBody UserRequest request) {
        var user = userService.saveUser(UserMapper.convertToModel(request));
        var useResponse = UserMapper.convertToResponse(user);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{Id}")
                        .buildAndExpand(useResponse.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(useResponse);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID idUser) {
        var user = userService.findById(idUser);
        return ResponseEntity.ok(UserMapper.convertToResponse(user));
    }

    @PatchMapping("/profile")
    public ResponseEntity<Void> insertProfile(@RequestParam("idUser") UUID idUser,
                                              @RequestParam("idProfile") UUID idProfile) {
        userService.addProfile(idUser, idProfile);
        return ResponseEntity.accepted().build();
    }

}
