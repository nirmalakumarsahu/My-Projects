package com.sahu.organizo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/vendors/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				authorize -> 
				authorize
						.requestMatchers("/", "/login", "/registration", "/forget-password", "/activation",
								"/reset-password")
						.permitAll()
					.requestMatchers("/api/auth/**").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(
						formLogin -> 
							formLogin.loginPage("/login")
							.defaultSuccessUrl("/dashboard")
				)
				.logout( logout ->
						logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
				)
				.rememberMe(Customizer.withDefaults());

		return http.build();
	}

}
