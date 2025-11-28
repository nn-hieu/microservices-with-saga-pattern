package com.hieunn.productservice.entities;

import com.hieunn.commonlib.entities.TimestampedBase;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product extends TimestampedBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean isActive = true;
}
