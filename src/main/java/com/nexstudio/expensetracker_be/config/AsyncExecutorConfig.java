package com.nexstudio.expensetracker_be.config;

import com.nexstudio.expensetracker_be.properties.RedisPublishExecutorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class AsyncExecutorConfig {
    private final RedisPublishExecutorProperties executorProperties;

    @Bean(name = "redisStreamPublisherExecutor")
    public Executor redisStreamPublisherExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(executorProperties.getCorePoolSize());
        executor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        executor.setQueueCapacity(executorProperties.getQueueCapacity());
        executor.setThreadNamePrefix(executorProperties.getExecutorName());

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(executorProperties.getAwaitTerminationSeconds());

        executor.initialize();
        return executor;
    }
}
