package com.sahu.um.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@PropertySources({
	@PropertySource("classpath:properties/error.properties"),
	@PropertySource("classpath:properties/header_footer.properties"),
	@PropertySource("classpath:properties/login.properties"),
	@PropertySource("classpath:properties/role.properties"),
	@PropertySource("classpath:properties/user.properties")
})
public class AppConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
