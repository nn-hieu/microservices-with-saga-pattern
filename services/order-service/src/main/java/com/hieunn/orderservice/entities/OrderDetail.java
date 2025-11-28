package com.hieunn.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @PrePersist
    @PreUpdate
    protected void calculateAmount() {
        if (quantity != null && unitPrice != null) {
            this.amount = quantity * unitPrice;
        } else {
            this.amount = 0;
        }
    }
}
