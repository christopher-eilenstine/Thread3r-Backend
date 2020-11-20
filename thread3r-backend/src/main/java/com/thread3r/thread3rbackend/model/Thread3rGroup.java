package com.thread3r.thread3rbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "group", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@NoArgsConstructor
public class Thread3rGroup extends Thread3rEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 45)
    private String name;

    @NotBlank
    @Size(max = 600)
    private String description;

    @Column(name = "group_user_id")
    @NotNull
    private Long userId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_membership",
                joinColumns = @JoinColumn(name = "membership_group_id"),
                inverseJoinColumns = @JoinColumn(name = "membership_user_id"))
    private Set<Thread3rUser> users = new HashSet<>();

    public Thread3rGroup(Long userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

}
