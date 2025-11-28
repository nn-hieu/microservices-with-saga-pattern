package com.hieunn.userservice.entities;

import com.hieunn.commonlib.entities.TimestampedBase;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends TimestampedBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            optional = false,
            mappedBy = "user"
    )
    private Wallet wallet;

    @PrePersist
    protected void createWallet() {
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(this);
        }
    }
}
