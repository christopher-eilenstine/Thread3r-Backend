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
@Table(name = "thread")
@NoArgsConstructor
public class Thread3rThread {

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
    @Size(max = 60)
    private String title;

    @NotBlank
    @Size(max = 600)
    private String content;

    @NotNull
    @Column(name = "thread_group_id")
    private Long groupId;

    @NotNull
    @Column(name = "thread_user_id")
    private Long userId;

    public Thread3rThread(String title, String content, Long groupId, Long userId) {
        this.title = title;
        this.content = content;
        this.groupId = groupId;
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
