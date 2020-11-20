package com.thread3r.thread3rbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_membership", indexes = {
            @Index(columnList = "group_id", name = "ix_membership_groupid"),
            @Index(columnList = "user_id", name = "ix_membership_userid")
    },
            joinColumns = @JoinColumn(name = "group_id",
                    foreignKey = @ForeignKey(name = "fk_membership_group")),
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    foreignKey = @ForeignKey(name = "fk_membership_user"))
    )
    private Set<UserEntity> members;

}
