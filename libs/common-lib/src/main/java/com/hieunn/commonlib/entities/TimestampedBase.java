package com.hieunn.commonlib.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class TimestampedBase extends CreatedAtBase {
    @Column(nullable = false)
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;
}
