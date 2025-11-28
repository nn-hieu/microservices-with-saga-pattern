package com.hieunn.orderservice.mappers;

import com.hieunn.commonlib.dtos.orders.OrderDTO;
import com.hieunn.orderservice.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { OrderDetailMapper.class })
public interface OrderMapper {
    OrderDTO.Response toDTO(Order order);
}
