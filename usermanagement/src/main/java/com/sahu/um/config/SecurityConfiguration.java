package com.sahu.um.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sahu.um.service.impl.UserDetailsServiceImpl;

@Configurable
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired(required = true)
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/lib/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request->
				request.antMatchers("/", "/login-process/**", "/forgetPassword").permitAll()
				.anyRequest().authenticated()
			)
			.csrf().disable()
			.formLogin(form -> form
					.loginPage("/login-process/login")
					.loginProcessingUrl("/login")
					.failureUrl("/login-process/login?error")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/user/dashboard", true))
			.logout(logout -> logout 
					.logoutUrl("/logout")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/login-process/login?logout"))
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.invalidSessionUrl("/invalidsession")
					.maximumSessions(1)
					.maxSessionsPreventsLogin(true)
					.expiredUrl("/login-process/login?session-expire"))
			.exceptionHandling()
			.accessDeniedHandler(null);
		return http.build();
	}
	
}
