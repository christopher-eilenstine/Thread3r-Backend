package com.thread3r.thread3rbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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
    private String content;

}
