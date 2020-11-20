package com.thread3r.thread3rbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thread3r_user", uniqueConstraints = {
        @UniqueConstraint(name = "uc_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uc_username", columnNames = {"username"})
})
public class UserEntity extends Thread3rEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 50)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 25)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    /*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_membership",
            joinColumns = @JoinColumn(name = "membership_user_id"),
            inverseJoinColumns = @JoinColumn(name = "membership_group_id"))
    private Set<Thread3rGroup> groups = new HashSet<>();
    */

}
