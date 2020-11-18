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
@Table(name = "group", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@NoArgsConstructor
public class Thread3rGroup {

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
    @Size(max = 45)
    private String name;

    @NotBlank
    @Size(max = 600)
    private String description;

    @Column(name = "group_user_id")
    @NotNull
    private Long userId;

    public Thread3rGroup(Long userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
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
