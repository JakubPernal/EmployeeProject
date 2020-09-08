package com.pernal.connection;

import com.pernal.persistence.connection.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class HibernateConnectionTest {

    @Test
    public void shouldConnectToDb() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Assert.assertNotNull(sessionFactory);
    }
}
