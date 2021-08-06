package br.com.challengebackend.aluraflixapi.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void shouldGeneratedId() {
        var user = UserClient.builder().id(null).build();

        user.generateId();

        assertThat(user.getId()).isNotNull();
    }
}
