package com.hieunn.orderservice.services;

import com.hieunn.commonlib.dtos.orders.OrderDto;
import com.hieunn.commonlib.enums.status.OrderStatus;

public interface OrderService {
    OrderDto.Response create(OrderDto.CreateRequest request);

    void updateOrderStatus(Integer id, OrderStatus status);

    void updateOrderFailed(Integer id);

    void updateOrderCompleted(Integer id);
}
