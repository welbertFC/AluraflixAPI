package br.com.challengebackend.aluraflixapi.models;

import br.com.challengebackend.aluraflixapi.dto.VideoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Video {

    @Id
    private UUID id;

    private String title;
    private String description;
    private String url;

    @ManyToOne
    private Category category;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    public Video(Video video, VideoRequest videoRequest) {
        this.id = video.getId();
        this.title = videoRequest.getTitle();
        this.description = videoRequest.getDescription();
        this.url = videoRequest.getUrl();
        this.createDateTime = video.getCreateDateTime();
    }

    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
