package com.hieunn.commonlib.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SagaEventDto {
    private Integer id;
    private String sagaId;
    private String eventName;
    private String payload;
    private String sourceService;
}
