package com.hieunn.commonlib.mappers;

import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.commonlib.entities.SagaEventBase;

public class SagaEventBaseMapper<T extends SagaEventBase> {
    public SagaEventDto toDto(T sagaEvent) {
        SagaEventDto dto = new SagaEventDto();

        dto.setSagaId(sagaEvent.getSagaId());
        dto.setEventName(sagaEvent.getEventName());
        dto.setPayload(sagaEvent.getPayload());
        dto.setSourceService(sagaEvent.getSourceService());

        return dto;
    }
}
