package com.hieunn.orderservice.entities;

import com.hieunn.commonlib.entities.TimestampedBase;
import com.hieunn.commonlib.enums.status.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order extends TimestampedBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetail detail) {
        if (orderDetails == null) {
            orderDetails = new ArrayList<>();
        }
        orderDetails.add(detail);
        detail.setOrder(this);
    }

    @PrePersist
    protected void calculateTotalAmount() {
        totalAmount = 0;
        for (OrderDetail detail : orderDetails) {
            totalAmount += detail.getQuantity() * detail.getUnitPrice();
        }
    }
}