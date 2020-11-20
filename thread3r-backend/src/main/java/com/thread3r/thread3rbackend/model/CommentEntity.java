package com.thread3r.thread3rbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thread3r_comment", indexes = {
        @Index(columnList = "thread_id", name = "ix_comment_threadid"),
        @Index(columnList = "user_id", name = "ix_comment_userid")
})
public class CommentEntity extends Thread3rEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thread_id", nullable = false)
    private Long threadId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Size(max = 600)
    @Column(name = "content", nullable = false)
    private String content;

}
