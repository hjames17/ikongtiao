package com.wetrack.ikongtiao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wetrack.base.dao.factory.CommonDaoFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by zhanghong on 16/7/23.
 */
@Configuration
@Import(BaseConfig.class)
@EnableJpaRepositories(basePackages = "com.wetrack.ikongtiao.repo.jpa")
@EnableTransactionManagement
@PropertySource("classpath:conf/jdbc.conf")
public class DataBaseConfig {

    @Value("${jdbc.driver}")
    String driver;
    @Value("${jdbc.wetrack.url}")
    String url;
    @Value("${jdbc.wetrack.user}")
    String user;
    @Value("${jdbc.wetrack.password}")
    String password;
    @Value("${jdbc.initialPoolSize}")
    private int initialPoolSize;
    @Value("${jdbc.minPoolSize}")
    private int minPoolSize;
    @Value("${jdbc.maxPoolSize}")
    private int maxPoolSzie;
    @Value("${jdbc.maxIdleTime}")
    private int maxIdleTime;


    @Bean
    public DataSource dataSource() throws PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(driver);
        dataSource.setPreferredTestQuery("select 1");
        dataSource.setInitialPoolSize(initialPoolSize);

        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSzie);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setIdleConnectionTestPeriod(60);
        dataSource.setCheckoutTimeout(2000);

        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        return dataSource;
    }


    @Autowired
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setPackagesToScan("com.wetrack.ikongtiao.domain", "com.wetrack.ikongtiao.dto");
        factory.setDataSource(dataSource);
        return factory;
    }

    @Autowired
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory){

        //这个DataSourceTransactionManager导致持久化没有实际完成
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(dataSource);
//        return dataSourceTransactionManager;

        JpaTransactionManager txManager = new JpaTransactionManager();

        txManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return txManager;
    }

    @Autowired
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager);

        return transactionTemplate;
    }


    /**
     * for mybatis
     * @return
     */
    @Autowired
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext context) throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        Resource resource = context.getResource("classpath:mybatis/sqlMapConfig.xml");//new ClassPathResource("classpath*:mybatis/sqlMapConfig.xml");
        sessionFactory.setConfigLocation(resource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = new Resource[]{
//                context.getResources("classpath*:mybatis/ikongtiao*/*.xml"),
//                context.getResources("classpath*:mybatis/ikongtiao-mix/*.xml")};
        sessionFactory.setMapperLocations(context.getResources("classpath*:mybatis/ikongtiao*/*.xml"));
//        sessionFactory.setTypeAliasesPackage("studio.wetrack.mhb.model");
        return sessionFactory.getObject();
    }

    @Autowired
    @Bean
    public CommonDaoFactoryBean commonDao(SqlSessionFactory sessionFactory){
        CommonDaoFactoryBean commonDao = new CommonDaoFactoryBean();
        commonDao.setSqlSessionFactory(sessionFactory);
        return commonDao;
    }
}
