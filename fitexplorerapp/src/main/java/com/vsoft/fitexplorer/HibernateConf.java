package com.vsoft.fitexplorer;

import org.apache.catalina.connector.Connector;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConf {
    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"com.vsoft.fitexplorer",
                        "com.vsoft.fitexplorer.jpl",
                        "com.vsoft.fitexplorer.entity"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }


    @Bean
    public DataSource dataSource() {
            String dbhost = StringUtils.defaultIfEmpty(System.getenv("DB_SERV"), "localhost");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + dbhost + ":5432/fitexplorer1");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123");

        return dataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                  "hibernate.hbm2ddl.auto", "none");
//                "hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        hibernateProperties.setProperty(
                "hibernate.jdbc.batch_size", "500");
        hibernateProperties.setProperty(
                "hibernate.order_inserts", "true");
        hibernateProperties.setProperty(
                "hibernate.order_updates", "true");
        hibernateProperties.setProperty(
                "hibernate.batch_versioned_data ", "true");


        hibernateProperties.setProperty("hibernate.format_sql","true");
        hibernateProperties.setProperty("spring.jpa.properties.hibernate.show_sql", "false");

        return hibernateProperties;
    }
}