package com.sahu.digital.marketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@PropertySources({ @PropertySource("classpath:properties/admin.properties"),
		@PropertySource("classpath:properties/authentication.properties"),
		@PropertySource("classpath:properties/error.properties"),
		@PropertySource("classpath:properties/header_footer.properties"),
		@PropertySource("classpath:properties/index.properties"),
		@PropertySource("classpath:properties/mail.properties"),
		@PropertySource("classpath:properties/role.properties"),
		@PropertySource("classpath:properties/user.properties") })
public class AppConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
