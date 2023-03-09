package com.cts.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //here we create beans directly
public class SpringSecurityConfiguration {
	//Inorder to store username we use LDAP or Database.
	//Here we use InMemory, i.e., H2
	
//	InMemoryUserDetailsManager
//	InMemoryUserDetailsManager(UserDetails... users)
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		//UserDetails is interface, so we cannot create objects directly
		//So we use builder class
		
		UserDetails userDetails1 = createNewUser("vishal", "dummy");
		UserDetails userDetails2 = createNewUser("gattadi", "dummydummy");
										
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder //takes i/p as string and o/p as string
		= input -> passwordEncoder().encode(input); 
		
		UserDetails userDetails = User.builder()
										.passwordEncoder(passwordEncoder)
										.username(username)
										.password(password)
										.roles("USER", "ADMIN")
										.build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()); 
		http.formLogin(withDefaults());
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		return http.build();
	}
}


// BCryptPasswordEncoder = Is Password Encoder uses Bcrypt strong hashing function.

/*
 * By default we get 2 features
 * 1. AllURLs are protected
 * 2. A login form is shown for unauthorized requests.
 * 
 * In order to run h2
 * 1. Disable Cross Side Request Forgery(CSRF)
 * 2. H2 uses Frames, which are taken from HTML. Frames are restricted in Spring Security
 */
//SecurityFilterChain - Defines a filter chain matched against every Http request