package br.com.challengebackend.aluraflixapi.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    @Test
    void shouldGeneratedId() {
        var profile = Profile.builder().id(null).build();

        profile.generateId();

        assertThat(profile.getId()).isNotNull();
    }
}

