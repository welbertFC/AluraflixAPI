package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.dto.UserRequest;
import br.com.challengebackend.aluraflixapi.dto.UserResponse;
import br.com.challengebackend.aluraflixapi.mappers.UserMapper;
import br.com.challengebackend.aluraflixapi.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "User")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "Insert new user")
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
    @ApiOperation(value = "Find user by ID")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID idUser) {
        var user = userService.findById(idUser);
        return ResponseEntity.ok(UserMapper.convertToResponse(user));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/profile")
    @ApiOperation(value = "Add Profile")
    public ResponseEntity<Void> insertProfile(@RequestParam("idUser") UUID idUser,
                                              @RequestParam("idProfile") UUID idProfile) {
        userService.addProfile(idUser, idProfile);
        return ResponseEntity.accepted().build();
    }

}
