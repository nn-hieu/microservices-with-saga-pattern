package com.hieunn.commonlib.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class ProcessedSagaEventBase extends CreatedAtBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 40)
    private String sagaId;

    @Column(nullable = false, length = 30)
    private String eventName;
}