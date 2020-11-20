package com.thread3r.thread3rbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "group", uniqueConstraints = {
        @UniqueConstraint(name = "uc_group_name", columnNames = {"name"})
}, indexes = {
        @Index(columnList = "group_user_id", name = "ix_group_user_id")
})
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity extends Thread3rEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 45)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotBlank
    @Size(max = 600)
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_user_id", foreignKey = @ForeignKey(name = "fk_group_user_id"))
    private UserEntity user;

    /*
    @NotNull
    @Column(name = "group_user_id", nullable = false)
    private Long userId;
    */

    /*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_membership",
                joinColumns = @JoinColumn(name = "membership_group_id"),
                inverseJoinColumns = @JoinColumn(name = "membership_user_id"))
    private Set<Thread3rUser> users = new HashSet<>();
    */

}
