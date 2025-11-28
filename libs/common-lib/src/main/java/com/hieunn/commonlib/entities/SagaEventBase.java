package com.hieunn.commonlib.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class SagaEventBase extends CreatedAtBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 40)
    private String sagaId;

    @Column(nullable = false, length = 30)
    private String eventName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private String sourceService;

    @Column(nullable = false)
    private Integer retryCount = 0;

    private LocalDateTime lastAttemptAt;
}
