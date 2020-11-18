package com.thread3r.thread3rbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 25)
    private String username;

    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_membership",
            joinColumns = @JoinColumn(name = "membership_user_id"),
            inverseJoinColumns = @JoinColumn(name = "membership_group_id"))
    private Set<Thread3rGroup> groups = new HashSet<>();

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
