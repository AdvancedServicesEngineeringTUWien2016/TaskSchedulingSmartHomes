package com.ase.iec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception{
		
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
								
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http                
				.httpBasic()
			.and()
				.authorizeRequests()
            		.antMatchers("/").permitAll()
            		.antMatchers("/register").permitAll()
            		.antMatchers("/current").permitAll()
            		.antMatchers("/getTasks").authenticated()
            		.antMatchers("/registerTask").authenticated()
            		.anyRequest().permitAll()
            		
            		
            		
			
			.and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf().csrfTokenRepository(csrfTokenRepository());

        
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName("X-XSRF-TOKEN");
    	return repository;
    }
}
