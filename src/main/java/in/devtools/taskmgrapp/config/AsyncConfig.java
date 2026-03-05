package in.devtools.taskmgrapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    @Bean(name = "assignmentTaskExecutor")
    public Executor assignmentTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4);       // 4 threads always alive
        executor.setMaxPoolSize(10);       // burst up to 10 threads
        executor.setQueueCapacity(50);     // queue up to 50 chunks before blocking
        executor.setThreadNamePrefix("assignment-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);  // graceful shutdown
        executor.initialize();

        return executor;
    }
}