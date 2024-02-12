package com.sahu.digital.marketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sahu.digital.marketing.model.User;
import com.sahu.digital.marketing.service.impl.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfiguration {

	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareImpl();
	}

}
