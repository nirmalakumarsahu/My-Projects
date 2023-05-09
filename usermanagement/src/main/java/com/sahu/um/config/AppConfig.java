package com.sahu.um.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sahu.um.service.impl.AuditorAwareImpl;

@Component
@Configurable
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppConfig {

	@Bean
	public AuditorAware<Long> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
