package com.hieunn.orderservice.services.impls;

import com.hieunn.commonlib.dtos.orders.OrderDto;
import com.hieunn.commonlib.dtos.orders.OrderDetailDto;
import com.hieunn.commonlib.dtos.products.ProductDto;
import com.hieunn.commonlib.enums.status.OrderStatus;
import com.hieunn.commonlib.exceptions.NotFoundException;
import com.hieunn.orderservice.clients.ProductClient;
import com.hieunn.orderservice.entities.Order;
import com.hieunn.orderservice.entities.OrderDetail;
import com.hieunn.orderservice.mappers.OrderMapper;
import com.hieunn.orderservice.publishers.OrderEventPublisher;
import com.hieunn.orderservice.repositories.OrderRepository;
import com.hieunn.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    @Transactional
    public OrderDto.Response create(OrderDto.CreateRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());

        for (OrderDetailDto.DetailCreateRequest detail : request.getOrderDetails()) {
            ProductDto.Response product = productClient.getById(detail.getProductId()).getData();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setProductId(product.getId());
            orderDetail.setUnitPrice(product.getPrice());

            order.addOrderDetail(orderDetail);
        }

        orderRepository.save(order);

        OrderDto.Response response = orderMapper.toResponse(order);

        orderEventPublisher.publishOrderCreatedSucceededEvent(response);

        log.info("Create new order with user id: {}", order.getUserId());

        return response;
    }

    @Override
    @Transactional
    public void updateOrderStatus(Integer id, OrderStatus status) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> {
                    log.error("Order not found with id: {}", id);

                    return new NotFoundException("Order not found with id: " + id);
                });

        order.setStatus(status);
        orderRepository.save(order);

        log.info("Update order status with user id: {} and status: {}", order.getUserId(), order.getStatus());
    }

    @Override
    @Transactional
    public void updateOrderFailed(Integer id) {
        this.updateOrderStatus(id, OrderStatus.FAILED);
    }

    @Override
    @Transactional
    public void updateOrderCompleted(Integer id) {
        this.updateOrderStatus(id, OrderStatus.COMPLETED);
    }
}
