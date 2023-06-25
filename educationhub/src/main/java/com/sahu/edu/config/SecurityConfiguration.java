package com.sahu.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/lib/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request->
				request.antMatchers("/", "/login", "/registration", "/forgetpassword").permitAll()
				.anyRequest().authenticated()
			)
			.csrf().disable()
			.formLogin(form -> form
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.failureUrl("/login?error")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/client/user/dashboard", true))
			.logout(logout -> logout 
					.logoutUrl("/logout")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/login?logout"))
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.invalidSessionUrl("/invalidsession")
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
					.expiredUrl("/login?session-expire"))
			.exceptionHandling()
			.accessDeniedPage("/accessdenied");
		return http.build();
	}
	
}
