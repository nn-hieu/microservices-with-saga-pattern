package com.hieunn.orderservice.mappers;

import com.hieunn.commonlib.dtos.orders.OrderDto;
import com.hieunn.orderservice.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { OrderDetailMapper.class })
public interface OrderMapper {
    OrderDto.Response toResponse(Order order);
}
