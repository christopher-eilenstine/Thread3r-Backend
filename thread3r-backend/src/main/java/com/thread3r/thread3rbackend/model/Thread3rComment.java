package com.thread3r.thread3rbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Thread3rComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @NotNull
    private Date createdTs;

    @NotNull
    private Boolean deleted;

    private Date deletedTs;

    @NotBlank
    @Size(max = 600)
    private String content;

    @NotNull
    @Column(name = "comment_thread_id")
    private Long threadId;

    @NotNull
    @Column(name = "comment_user_id")
    private Long userId;

    public Thread3rComment(String content, Long threadId, Long userId) {
        this.content = content;
        this.threadId = threadId;
        this.userId = userId;
    }

    @PrePersist
    public void prePersist() {
        if (createdTs == null) {
            createdTs = new Date();
        }
        if (deleted == null) {
            deleted = Boolean.FALSE;
        }
    }

}
