package com.hieunn.commonlib.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class CreatedAtBase {
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;
}
