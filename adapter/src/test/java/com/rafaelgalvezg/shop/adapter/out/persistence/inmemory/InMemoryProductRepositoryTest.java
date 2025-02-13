package com.rafaelgalvezg.shop.adapter.out.persistence.inmemory;

import com.rafaelgalvezg.shop.adapter.out.persistence.AbstractProductRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class InMemoryProductRepositoryTest extends AbstractProductRepositoryTest {
}