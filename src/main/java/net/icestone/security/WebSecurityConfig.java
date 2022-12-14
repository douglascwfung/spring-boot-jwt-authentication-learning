package net.icestone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.icestone.security.jwt.AuthEntryPointJwt;
import net.icestone.security.jwt.AuthTokenFilter;
import net.icestone.security.jwt.CustomAccessDeineHandler;
import net.icestone.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Autowired
	private CustomAccessDeineHandler accessDeineHandler;

	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	
	// commented out by me, and check it is ok to run without it
//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
	
	// For user sign in
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			//
			.and()
			.csrf().disable()
			//
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedHandler)
			.accessDeniedHandler(accessDeineHandler)
			//
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//
			.and()
			.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/test/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated();
		
		http.headers().frameOptions().disable();
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
}

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//			.authorizeRequests()
//			.antMatchers("/api/test/**").authenticated()
//			.antMatchers("/h2-console/**").permitAll()
//			.anyRequest().authenticated()
//			.and().httpBasic()
//	        ;
//		http.headers().frameOptions().disable();
//	}
//
//}
