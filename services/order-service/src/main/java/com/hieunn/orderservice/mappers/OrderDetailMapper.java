package com.hieunn.orderservice.mappers;

import com.hieunn.commonlib.dtos.orders.OrderDetailDTO;
import com.hieunn.orderservice.entities.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailDTO.Response toDTO(OrderDetail orderDetail);
}
