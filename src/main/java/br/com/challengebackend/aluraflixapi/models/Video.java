package br.com.challengebackend.aluraflixapi.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Video {

    @Id
    private UUID id;

    private String title;
    private String description;
    private String url;

    @CreatedDate
    private LocalDateTime localDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
