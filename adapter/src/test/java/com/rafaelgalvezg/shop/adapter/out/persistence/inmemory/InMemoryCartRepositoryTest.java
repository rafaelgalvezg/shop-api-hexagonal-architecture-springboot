package com.rafaelgalvezg.shop.adapter.out.persistence.inmemory;

import com.rafaelgalvezg.shop.adapter.out.persistence.AbstractCartRepositoryTest;

class InMemoryCartRepositoryTest
        extends AbstractCartRepositoryTest<InMemoryCartRepository, InMemoryProductRepository> {

    @Override
    protected InMemoryCartRepository createCartRepository() {
        return new InMemoryCartRepository();
    }

    @Override
    protected InMemoryProductRepository createProductRepository() {
        return new InMemoryProductRepository();
    }
}