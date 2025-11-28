package com.hieunn.orderservice.mappers;

import com.hieunn.commonlib.dtos.orders.OrderDetailDto;
import com.hieunn.orderservice.entities.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailDto.Response toDTO(OrderDetail orderDetail);
}
