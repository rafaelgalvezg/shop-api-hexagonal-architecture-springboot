package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public final class EntityManagerFactoryFactory {

    private EntityManagerFactoryFactory(){}

    public static EntityManagerFactory createMysqlEntityManagerFactory
            (String jdbcUrl, String user, String password){
        return Persistence.createEntityManagerFactory(
                "com.rafaelgalvezg.shop.adapter.out.persistence.jpa",
                Map.of(
                        "hibernate.dialect",
                        "org.hibernate.dialect.MySQLDialect",
                        "hibernate.hbm2ddl.auto",
                        "update",
                        "jakarta.persistence.jdbc.driver",
                        "com.mysql.jdbc.Driver",
                        "jakarta.persistence.jdbc.url",
                        jdbcUrl,
                        "jakarta.persistence.jdbc.user",
                        user,
                        "jakarta.persistence.jdbc.password",
                        password
                )
        );
    }

}
