package com.hieunn.orderservice.publishers;

import com.hieunn.commonlib.dtos.orders.OrderDto;

public interface OrderEventPublisher {
    void publishOrderCreatedSucceededEvent(OrderDto.Response order);
}
