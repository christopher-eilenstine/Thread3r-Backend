package com.thread3r.thread3rbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@MappedSuperclass
public abstract class Thread3rEntity implements Serializable {

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTimestamp;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "deleted_ts")
    private LocalDateTime deletedTimestamp;

    public void markSoftDeleted(LocalDateTime timestamp) {
        deleted = true;
        deletedTimestamp = timestamp;
    }

    @PrePersist
    protected void onPersist() {
        createdTimestamp = Optional.ofNullable(createdTimestamp).orElse(LocalDateTime.now());
    }
}
