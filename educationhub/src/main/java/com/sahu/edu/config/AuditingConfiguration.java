package com.sahu.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sahu.edu.service.impl.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfiguration {

	@Bean
	public AuditorAware<Long> auditorAware() {
		return new AuditorAwareImpl();
	}

}
