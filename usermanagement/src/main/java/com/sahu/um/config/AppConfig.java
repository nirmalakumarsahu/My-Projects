package com.sahu.um.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sahu.um.service.impl.AuditorAwareImpl;

@Configurable
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppConfig {

	@Bean
	public AuditorAware<Long> auditorAware() {
		return new AuditorAwareImpl();
	}
	
}
