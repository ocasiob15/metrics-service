package cygnus.platform.metric.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"cygnus.platform.metric.dal"})
public class JpaConfiguration {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.jpa.database-platform}")
    private String dialect;

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpm = new JpaTransactionManager();
        jpm.setEntityManagerFactory(emf().getObject());
        jpm.setDataSource(dataSource);
        return jpm;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(
                new String[] {"cygnus.platform.metric.model"});
        emf.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, dialect);
        emf.setJpaProperties(properties);
        return emf;
    }
}
