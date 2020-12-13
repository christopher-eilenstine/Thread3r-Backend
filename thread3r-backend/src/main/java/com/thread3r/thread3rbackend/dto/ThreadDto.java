package com.thread3r.thread3rbackend.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDto {

    private Long id;

    private LocalDateTime created;

    private Long creatorId;

    private String creator;

    private Long group;

    @NotNull
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @Size(min = 1, max = 600)
    private String content;
}