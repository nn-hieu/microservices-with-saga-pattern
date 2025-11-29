package com.hieunn.deadletterservice.services.impls;

import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.deadletterservice.entities.SagaEvent;
import com.hieunn.deadletterservice.repositories.SagaEventRepository;
import com.hieunn.deadletterservice.services.SagaEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaEventServiceImpl implements SagaEventService {
    private final SagaEventRepository sagaEventRepository;

    @Override
    @Transactional
    public SagaEvent save(SagaEventDto event, String queue, String exchange) {
        SagaEvent sagaEvent = new SagaEvent();
        sagaEvent.setSagaId(event.getSagaId());
        sagaEvent.setEventName(event.getEventName());
        sagaEvent.setPayload(event.getPayload());
        sagaEvent.setSourceService(event.getSourceService());
        sagaEvent.setQueue(queue);
        sagaEvent.setExchange(exchange);

        return sagaEventRepository.save(sagaEvent);
    }
}
