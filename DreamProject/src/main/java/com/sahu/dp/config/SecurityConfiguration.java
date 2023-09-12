package com.sahu.dp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.sahu.dp.config.handler.OAuthLoginSuccessHandler;
import com.sahu.dp.service.impl.OAuth2UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private OAuth2UserServiceImpl oAuth2UserServiceImpl;
	
	@Autowired
	private OAuthLoginSuccessHandler  authLoginSuccessHandler;
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/lib/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/", "/login", "/registration", "/forget-password", 
						"/reset-password", "/change-password").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.failureUrl("/login?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/client/user/dashboard", true)
			.and()
			.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(oAuth2UserServiceImpl)
				.and()
				.successHandler(authLoginSuccessHandler)
			.and()
			.csrf().disable()
			.logout() 
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login?logout")
			.and()
			.exceptionHandling()
				.accessDeniedPage("/access-denied")
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.invalidSessionUrl("/invalid-session")
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false)
				.expiredUrl("/login?session-expire");
			
		return http.build();
	}
	
}
