package com.thread3r.thread3rbackend.model;

import lombok.Data;
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
@Table(name = "thread")
@NoArgsConstructor
public class Thread3rThread extends Thread3rEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String title;

    @NotBlank
    @Size(max = 600)
    private String content;

    @Column(name = "thread_group_id", nullable = false)
    private Long groupId;

    @Column(name = "thread_user_id", nullable = false)
    private Long userId;

    public Thread3rThread(String title, String content, Long groupId, Long userId) {
        this.title = title;
        this.content = content;
        this.groupId = groupId;
        this.userId = userId;
    }

}
