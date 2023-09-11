package com.gsc.programaavisos.config.datasource.cardb;

import com.ibm.db2.jcc.DB2SimpleDataSource;
import com.sc.commons.dbconnection.ServerJDBCConnection;
import com.sc.commons.initialization.SCGlobalPreferences;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Profile(value = {"local"} )
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "carEntityManagerFactory",
        basePackages = {"com.gsc.programaavisos.repository.cardb"}
)
public class CarDBConfigLocal {


    @Autowired
    private Environment env;

    @Value("${sc.config.file}")
    private String scConfigFile;



    @PostConstruct
    private void init() {
        SCGlobalPreferences.setResources(scConfigFile);
        ServerJDBCConnection conn = ServerJDBCConnection.getInstance();
        DB2SimpleDataSource dbcardb = new DB2SimpleDataSource();
        dbcardb.setServerName("scdbesrva.sc.pt");
        dbcardb.setPortNumber(50000);
        dbcardb.setDatabaseName("CARDB");
        dbcardb.setDriverType(4);
        dbcardb.setUser("db2inst1");
        dbcardb.setPassword("db2admin");
        conn.setDataSource(dbcardb, "jdbc/cardb");
        log.info("Datasource initialized successfully: jdbc/cardb");
    }

    @Bean(name="carDataSource")
    @ConfigurationProperties(prefix = "car.datasource")
    DataSource dataSource(){
        return DataSourceBuilder.create()
                .url(env.getProperty("app.datasource.second.url"))
                .driverClassName(env.getProperty("app.datasource.second.driver-class-name"))
                .username(env.getProperty("app.datasource.second.username"))
                .password(env.getProperty("app.datasource.second.password"))
                .build();
    }

    @Bean(name = "carEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean carEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("carDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.gsc.programaavisos.model.cardb.entity")
                .persistenceUnit("carPersistenceUnit")
                .properties(getHibernateProperties())
                .build();
    }

    @Bean(name = "carTransactionManager")
    PlatformTransactionManager caryTransactionManager(@Qualifier("carEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> getHibernateProperties() {
        Map<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.DB2Dialect");
        return hibernateProperties;
    }

}
