package com.hieunn.orderservice.publishers;

import com.hieunn.commonlib.dtos.orders.OrderDTO;

public interface OrderEventPublisher {
    void publishOrderCreatedSucceededEvent(OrderDTO.Response order);
}
