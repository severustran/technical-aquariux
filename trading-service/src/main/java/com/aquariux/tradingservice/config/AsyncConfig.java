package com.aquariux.tradingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Value("${concurrent.properties.corePoolSize:20}")
    private int corePoolSize;

    @Value("${concurrent.properties.max-pool-size:200}")
    private int maxPoolSize;

    @Value("${concurrent.properties.queue-capacity:200}")
    private int queueCapacity;
    @Bean
    public TaskExecutor bestPriceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("bestPricingExec-");
        executor.initialize();
        return executor;
    }
}
