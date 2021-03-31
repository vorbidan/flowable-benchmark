package org.flowable.benchmark.app;

import javax.sql.DataSource;

import com.yugabyte.ysql.YBClusterAwareDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("ybdb")
public class YbdbConfiguration {
    @Value("${yugabyte.sql.datasource.host}")
    private String dbHost;

    @Value("${yugabyte.sql.datasource.port}")
    private String dbPort;

    @Value("${yugabyte.sql.datasource.name}")
    private String dbName;

    @Value("${yugabyte.sql.datasource.user}")
    private String jdbcUser;

    @Value("${yugabyte.sql.datasource.password}")
    private String jdbcPassword;

    @Value("${yugabyte.sql.datasource.pool.size}")
    private int poolSize;


    @Value("${yugabyte.ycql.user}")
    private String ycqlUser;

    @Value("${yugabyte.ycql.password}")
    private String ycqlPassword;

    @Bean
    public DataSource dataSource() {
        YBClusterAwareDataSource dataSource = new YBClusterAwareDataSource();
        dataSource.setInitialHost(dbHost);
        dataSource.setPassword(dbPort);
        dataSource.setDatabase(dbName);
        // dataSource.setYcqlCredentials(ycqlUser, ycqlPassword);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        dataSource.setMaxPoolSizePerNode(poolSize);
        dataSource.initialize();
        return dataSource;
    }

}
