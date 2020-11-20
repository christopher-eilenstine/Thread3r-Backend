package com.thread3r.thread3rbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thread3r_group", uniqueConstraints = {
        @UniqueConstraint(name = "uc_group_name", columnNames = {"group_name"})
}, indexes = {
        @Index(columnList = "user_id", name = "ix_group_1")
})
public class GroupEntity extends Thread3rEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Size(max = 45)
    @Column(name = "group_name", unique = true, nullable = false)
    private String name;

    @NotBlank
    @Size(max = 600)
    @Column(name = "description", nullable = false)
    private String description;

    /*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_membership",
                joinColumns = @JoinColumn(name = "membership_group_id"),
                inverseJoinColumns = @JoinColumn(name = "membership_user_id"))
    private Set<Thread3rUser> users = new HashSet<>();
    */

}
