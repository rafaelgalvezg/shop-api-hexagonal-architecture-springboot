package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Cart")
@Getter
@Setter
public class CartJpaEntity {

    @Id private int customerId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartLineItemJpaEntity> lineItems;

}
