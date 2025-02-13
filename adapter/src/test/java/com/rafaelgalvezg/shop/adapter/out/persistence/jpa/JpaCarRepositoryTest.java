package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import com.rafaelgalvezg.shop.adapter.out.persistence.AbstractCartRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test-with-mysql")
class JpaCarRepositoryTest extends AbstractCartRepositoryTest {

}