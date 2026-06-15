package code.async.asyncImplement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "apiExecutor")
    public Executor apiExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("api-async-");
        executor.initialize();
        return executor;
    }
    @Bean(name = "cpuExecutor", destroyMethod = "shutdown")
    public ExecutorService cpuExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(cores);
    }
}
/*
@Bean(name="apiExecutor")	Registers Spring bean
ThreadPoolTaskExecutor()	Creates thread pool
setCorePoolSize(5)	        Keep 5 threads alive
setMaxPoolSize(10)	        Can grow to 10 threads
setQueueCapacity(50)	    Hold 50 waiting tasks
setThreadNamePrefix()	    Custom thread names
initialize()	            Start thread pool
@Bean(name="cpuExecutor")	Registers CPU executor
destroyMethod="shutdown"	Cleanup on app shutdown
availableProcessors()	    Get CPU core count
newFixedThreadPool(cores)	Create one thread per core
 */
