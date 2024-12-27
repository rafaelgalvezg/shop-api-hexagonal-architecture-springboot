package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="Product")
@Getter
@Setter
public class ProductJpaEntity {

    @Id private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String priceCurrency;

    @Column(nullable = false)
    private BigDecimal priceAmount;

    private int itemsInStock;

}
