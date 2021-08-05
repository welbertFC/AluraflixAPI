package br.com.challengebackend.aluraflixapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private UUID id;
    private String profile;

    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
