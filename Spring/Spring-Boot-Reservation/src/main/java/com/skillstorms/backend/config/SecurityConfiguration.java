package com.skillstorms.backend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

// Spring Boot will auto @Import your configuration classes
// Adding this class will override the default behavior of Spring Security
@Configuration
// The deprecated class still works even when removed for backwards compatability reasons
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired // This gives me BCrypt
	private PasswordEncoder encoder;
	
	// Spring will recognize this name
	@Autowired // Autowires the AuthenticationManagerBuilder object using setter injection
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// In Spring Security there are multiple options for auth
		// In-Memory (Not production. For test purposes)
		// LDAP (Lightweight Directory Access Protocol) Linux, Windows Active Directory
		// SAML (SSO)
		// JDBC (Uses some SQL schema to store users and roles)
		
		/*
		 * Steps for JDBC Auth
		 * 1. Extract the username and password from the Authorization Header
		 * 2. Decode the Base64 encoded password and username
		 * 3. Hash the password with BCrypt
		 * 4. JDBC Auth select username, password, enabled from users where username = ?
		 * 5. If enabled == true ? proceed : 401 status code
		 * 6. At this point they are authenticated and proceed to DispatcherServlet
		 * 7. User Principal (User Details) is stored in the HttpSession
		 */
		
		// JDBC auth FORCES you to use a password encoder
		 auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
         .dataSource(datasource)
         .usersByUsernameQuery("select Username, Password, Enabled from User where Username=?")
         .authoritiesByUsernameQuery("select Username, Role from User where Username=?")
     ;
}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeHttpRequests().mvcMatchers("/customer/*","/reservation/*","/registration","hotel").permitAll();

		  //http.formLogin();
		  http.httpBasic();
		  http.cors();

		  http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/register","/customer/*");

		  http.csrf().disable(); //can't get this to work 
		  http.authorizeHttpRequests().mvcMatchers("/test/**").hasAnyRole("USER");

		  http.authorizeHttpRequests().mvcMatchers("/user/**").hasAnyRole("USER");
		  http.logout().deleteCookies("JSESSIONID").invalidateHttpSession(true);



  }
}