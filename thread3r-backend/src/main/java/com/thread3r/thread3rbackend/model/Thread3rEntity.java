package com.thread3r.thread3rbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

@Getter
@Setter
@MappedSuperclass
public abstract class Thread3rEntity implements Serializable {

    @Column(name = "created_ts", nullable = false)
    private Instant createdTimestamp;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "deleted_ts")
    private Instant deletedTimestamp;

    public void markSoftDeleted(Instant timestamp) {
        deleted = true;
        deletedTimestamp = timestamp;
    }

    @PrePersist
    protected void onPersist() {
        createdTimestamp = Optional.ofNullable(createdTimestamp).orElseGet(Instant::now);
    }
}
