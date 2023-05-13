package com.sahu.um.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sahu.um.service.impl.AuditorAwareImpl;

@Component
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@PropertySources({
	@PropertySource("classpath:properties/login.properties"),
	@PropertySource("classpath:properties/registration.properties"),
	@PropertySource("classpath:properties/forgetPassword.properties"),
	@PropertySource("classpath:properties/role.properties"),
	@PropertySource("classpath:properties/permission.properties")
})
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
