package com.thread3r.thread3rbackend.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;

    private LocalDateTime created;

    private Long creator;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    @Size(min = 1, max = 600)
    private String description;

}
