package com.brmals.application.api.config.database;

import com.brmals.application.api.constants.ApiConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.math.NumberUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = ApiConstants.MAIN_REPOSITORY_PACKAGE,
        entityManagerFactoryRef = ApiConstants.MAIN_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = ApiConstants.MAIN_TRANSACTION_MANAGER
)
public class MainDBConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private StringEncryptor stringEncryptor;

    @Bean(name = ApiConstants.MAIN_DATA_SOURCE_NAME,destroyMethod = "close")
    @Primary
    public DataSource mainDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_DRIVER_CLASS_NAME_KEY));
        hikariConfig.setJdbcUrl(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_URL_KEY));
        hikariConfig.setUsername(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_USERNAME_KEY));
        hikariConfig.setPassword(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_PASSWORD_KEY));
        hikariConfig.setDataSourceClassName(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_CLASS_NAME_KEY));

        hikariConfig.setMaximumPoolSize(NumberUtils.toInt(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_MAXIMUM_POOL_SIZE_KEY),10));
        hikariConfig.setIdleTimeout(NumberUtils.toLong(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_IDLE_TIMEOUT_KEY),30000));
        hikariConfig.setMaxLifetime(NumberUtils.toLong(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_MAX_LIFE_TIME_KEY),60000));
        //hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName(environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_POOL_NAME_KEY));

        hikariConfig.addDataSourceProperty("cachePrepStmts", environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_CACHE_PREP_STMTS_KEY,"true"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_PRE_STMT_CACHE_SIZE_KEY,"250"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_PRE_STMT_CACHE_SQL_LIMIT_KEY,"2048"));
        hikariConfig.addDataSourceProperty("useServerPrepStmts", environment.getProperty(ApiConstants.MAIN_DATA_SOURCE_USE_SERVER_PREP_STMTS_KEY,"true"));

        HikariDataSource mainDataSource = new HikariDataSource(hikariConfig);
        return mainDataSource;
    }

    @Bean(name = ApiConstants.MAIN_ENTITY_MANAGER_FACTORY)
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactoryBean(EntityManagerFactoryBuilder factoryBuilder, @Qualifier(ApiConstants.MAIN_DATA_SOURCE_NAME) DataSource mainDataSource) {
        return factoryBuilder
                .dataSource(mainDataSource)
                .packages(ApiConstants.MAIN_MODEL_PACKAGE)
                .persistenceUnit(ApiConstants.MAIN_DATA_SOURCE_PERSISTENCE_UNIT)
                .properties(hibernateProperties())
                .build();
    }

    @Bean(name = ApiConstants.MAIN_TRANSACTION_MANAGER)
    @Primary
    public PlatformTransactionManager mainTransactionManager(@Qualifier(ApiConstants.MAIN_ENTITY_MANAGER_FACTORY) EntityManagerFactory mainEntityManagerFactory) {
        return new JpaTransactionManager(mainEntityManagerFactory);
    }

    public static Map<String,?> hibernateProperties() {
        Map<String,Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.showSql",true);//if not worked, use "hibernate.show_sql"
        return hibernateProperties;
    }
}
