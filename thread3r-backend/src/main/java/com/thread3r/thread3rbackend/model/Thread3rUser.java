package com.thread3r.thread3rbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
public class Thread3rUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @NotNull
    private Date createdTs;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Thread3rUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @PrePersist
    protected void prePersist() {
        if (createdTs == null) {
            createdTs = new Date();
        }
    }
}
