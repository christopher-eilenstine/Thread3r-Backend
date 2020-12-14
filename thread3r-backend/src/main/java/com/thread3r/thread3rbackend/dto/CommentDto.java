package com.thread3r.thread3rbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private LocalDateTime created;

    private Long thread;

    private Long creatorId;

    private String creator;

    @NotNull
    @Size(min = 1, max = 600)
    private String content;

}
