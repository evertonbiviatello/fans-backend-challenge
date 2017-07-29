package com.fans.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(taskScheduler());
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduledExecutorService = new ThreadPoolTaskScheduler();
		scheduledExecutorService.setPoolSize(1);
		scheduledExecutorService.setThreadNamePrefix("myThread-");
		scheduledExecutorService.setWaitForTasksToCompleteOnShutdown(false);
		return scheduledExecutorService;
	}

	// @Bean
	// public TaskExecutor taskExecutor() {
	// ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	// taskExecutor.setThreadNamePrefix("myExecutor-");
	// taskExecutor.setCorePoolSize(10);
	// return taskExecutor;
	// }

}
