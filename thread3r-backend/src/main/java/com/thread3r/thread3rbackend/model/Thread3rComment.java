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
public class Thread3rComment extends Thread3rEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

}
