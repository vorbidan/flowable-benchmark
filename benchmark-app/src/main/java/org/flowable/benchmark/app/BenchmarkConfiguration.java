package org.flowable.benchmark.app;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import com.yugabyte.ysql.YBClusterAwareDataSource;

/**
 * @author Filip Hrisafov
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
public class BenchmarkConfiguration {

    protected final BenchmarkProperties properties;

    public BenchmarkConfiguration(BenchmarkProperties properties) {
        this.properties = properties;
    }

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> benchmarkProcessEngineConfigurer() {
        return engineConfiguration -> {
            engineConfiguration.setEnableEventDispatcher(false);
            BenchmarkProperties.AsyncHistory asyncHistory = properties.getAsyncHistory();
            if (asyncHistory.isEnabled()) {
                engineConfiguration.setAsyncHistoryEnabled(true);
                engineConfiguration.setAsyncExecutorActivate(true);
                engineConfiguration.setAsyncHistoryJsonGroupingEnabled(asyncHistory.isGrouping());
                engineConfiguration.setAsyncHistoryJsonGzipCompressionEnabled(asyncHistory.isGzip());
                engineConfiguration.setAsyncHistoryJsonGroupingThreshold(asyncHistory.getGroupingThreshold());
            }

            engineConfiguration.setCreateDiagramOnDeploy(false);

            engineConfiguration.getPerformanceSettings().setEnableEagerExecutionTreeFetching(properties.isTreeFetch());
        };
    }
}
