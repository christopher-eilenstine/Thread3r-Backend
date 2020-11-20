package com.thread3r.thread3rbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class CommentEntity extends Thread3rEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 600)
    private String content;

    @Column(name = "comment_thread_id", nullable = false)
    private Long threadId;

    @Column(name = "comment_user_id", nullable = false)
    private Long userId;

    public CommentEntity(String content, Long threadId, Long userId) {
        this.content = content;
        this.threadId = threadId;
        this.userId = userId;
    }

}
