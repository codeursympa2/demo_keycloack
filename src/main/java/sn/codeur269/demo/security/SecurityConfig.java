package sn.codeur269.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final String ADMIN="admin";
	private static final String USER="user";
	
	private final JwtConverter jwtConverter;

	public SecurityConfig(JwtConverter jwtConverter) {
		this.jwtConverter = jwtConverter;
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.GET,"/api/home").permitAll()
					.requestMatchers(HttpMethod.GET,"/api/user/**").hasRole(USER)
					.requestMatchers(HttpMethod.GET,"/api/admin/**").hasRole(ADMIN)
					.requestMatchers(HttpMethod.GET,"/api/user-admin/**").hasAnyRole(USER,ADMIN)
					.anyRequest().authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
			.build();
	
	}
	
	
}
