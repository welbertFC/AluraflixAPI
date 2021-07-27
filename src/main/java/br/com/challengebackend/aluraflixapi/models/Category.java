package br.com.challengebackend.aluraflixapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    private UUID id;
    private String title;
    private String color;

    @OneToMany(mappedBy = "category")
    private List<Video> videos;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void generateId(){
        this.id = UUID.randomUUID();
    }
}
