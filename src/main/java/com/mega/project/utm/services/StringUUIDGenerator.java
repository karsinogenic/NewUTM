package com.mega.project.utm.services;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class StringUUIDGenerator implements IdentifierGenerator {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj)
            throws HibernateException {
        return generateUUID();
    }
}
