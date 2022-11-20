package com.estore.api.estoreapi;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This is the core file that provides Spring Security configuration, it is what manages access based on roles to different endpoints and enables spring
 * security features
 * 
 * {@link @Configuration} is a spring annotation that indicates that this class has @Bean definition methods
 * Spring container will process this class to create Spring Beans
 * 
 * @author Team E
 */
@Configuration
@EnableWebSecurity
//Allows endpoints to only be exposed if that user has that specific role
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService jwtService;
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    /**
     * WebSecurityConfiguration Constructor
     * 
     * @param jwtService jwtService that will retrieve user Details
     */
    public WebSecurityConfiguration(UserDetailsService jwtService) {
        this.jwtService = jwtService;
    }
    /**
     * returns the container bean that will be the point for all authentication providers
     * 
     * @return AuthenticationManager object
     */
    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();    
    }   
    /**
     * returns the the password encoder to be used throughout the spring application which in this case will be BCrypt
     * 
     * @return passwordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Contains configuration for httpSecurity that implements role based access to pages, entry point and overall configurations
     * 
     * @param httpSecurity the object where configurations are set
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/products").hasRole("admin")
                    .antMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("admin")
                    .antMatchers(HttpMethod.PUT, "/products").hasRole("admin")
                    .antMatchers(HttpMethod.GET, "/products").permitAll()
                    .antMatchers(HttpMethod.GET, "/products/*").permitAll()
                    .antMatchers(HttpMethod.POST, "/roles**").hasRole("admin")
                    .antMatchers(HttpMethod.DELETE, "/roles**").hasRole("admin")
                    .antMatchers(HttpMethod.GET, "/roles**").permitAll()
                    .antMatchers(HttpMethod.POST, "/users").permitAll()
                    .antMatchers(HttpMethod.DELETE, "/users**").hasAnyRole("admin", "user")
                    .antMatchers(HttpMethod.PUT, "/users**").hasRole("user")
                    .antMatchers(HttpMethod.GET, "/users").hasRole("admin")
                    .antMatchers(HttpMethod.GET, "/users/{username:[a-zA-Z &+-]*}").hasAnyRole("admin", "user")
                    .antMatchers("/shoppingcart**").hasAnyRole("admin", "user")
                    .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .antMatchers(HttpHeaders.ALLOW).permitAll()
                    .anyRequest().authenticated();
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    /**
     * Configures userDetailsService and passwordEncoder that is to be used
     * 
     * @param authenticationManagerBuilder 
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        //configure JWT service which will handle all JWT related things to user password encoding
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }
}
