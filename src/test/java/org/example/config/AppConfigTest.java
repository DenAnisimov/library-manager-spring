package org.example.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppConfig.class)
class AppConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Autowired
    private JpaTransactionManager transactionManager;


    @Autowired
    private SpringLiquibase liquibase;

    @Test
    void testDataSource() {
        assertNotNull(dataSource, "DataSource should not be null");
        assertInstanceOf(BasicDataSource.class, dataSource, "DataSource should be of type BasicDataSource");

        org.apache.commons.dbcp2.BasicDataSource ds = (org.apache.commons.dbcp2.BasicDataSource) dataSource;
        assertEquals("org.postgresql.Driver", ds.getDriverClassName(), "Driver class name should be set correctly");
        assertEquals("jdbc:postgresql://localhost:5432/library_manager", ds.getUrl(), "URL should be set correctly");
        assertEquals("postgres", ds.getUserName(), "Username should be set correctly");
        assertEquals("secret", ds.getPassword(), "Password should be set correctly");
    }

    @Test
    void testEntityManagerFactory() {
        assertNotNull(entityManagerFactory, "EntityManagerFactory should not be null");
        assertNotNull(entityManagerFactory.getObject(), "EntityManagerFactory object should not be null");

        Map<String, Object> map = entityManagerFactory.getJpaPropertyMap();
        Properties properties = new Properties();
        properties.putAll(map);

        assertEquals("true", properties.getProperty("hibernate.show_sql"), "Property 'hibernate.show_sql' should be true");
        assertEquals("true", properties.getProperty("hibernate.format_sql"), "Property 'hibernate.format_sql' should be true");
        assertEquals("none", properties.getProperty("hibernate.hbm2ddl.auto"), "Property 'hibernate.hbm2ddl.auto' should be update");
        assertEquals("org.hibernate.dialect.PostgreSQLDialect", properties.getProperty("hibernate.dialect"), "Property 'hibernate.dialect' should be PostgreSQLDialect");
    }

    @Test
    void testTransactionManager() {
        assertNotNull(transactionManager, "TransactionManager should not be null");
        assertNotNull(transactionManager.getEntityManagerFactory(), "EntityManagerFactory should be set on TransactionManager");
        assertEquals(entityManagerFactory.getObject(), transactionManager.getEntityManagerFactory(), "TransactionManager should use the correct EntityManagerFactory");
    }

    @Test
    void testLiquibase() {
        assertNotNull(liquibase, "Liquibase should not be null");
        assertEquals(dataSource, liquibase.getDataSource(), "Liquibase should use the correct DataSource");
        assertEquals("classpath:db/changelog/db.changelog-master.xml", liquibase.getChangeLog(), "Liquibase change log should be set correctly");
    }
}
