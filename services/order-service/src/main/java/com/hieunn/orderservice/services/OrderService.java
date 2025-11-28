package com.hieunn.orderservice.services;

import com.hieunn.commonlib.dtos.orders.OrderDTO;
import com.hieunn.commonlib.enums.status.OrderStatus;

public interface OrderService {
    OrderDTO.Response create(OrderDTO.CreateRequest request);

    void updateOrderStatus(Integer id, OrderStatus status);

    void updateOrderFailed(Integer id);

    void updateOrderCompleted(Integer id);
}
