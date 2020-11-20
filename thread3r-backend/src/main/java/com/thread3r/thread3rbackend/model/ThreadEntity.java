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
@Table(name = "thread3r_thread", indexes = {
        @Index(columnList = "group_id", name = "ix_thread_groupid"),
        @Index(columnList = "user_id", name = "ix_thread_userid")
})
public class ThreadEntity extends Thread3rEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Size(max = 60)
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Size(max = 600)
    @Column(name = "content", nullable = false)
    private String content;

}
