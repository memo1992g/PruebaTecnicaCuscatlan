package backend.apiscart.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import backend.apiscart.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .csrf().disable() // Desactivar CSRF para simplificar el ejemplo
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/authentication/getToken").permitAll()
            .antMatchers(HttpMethod.POST, "/authentication/renewToken").permitAll()
            .antMatchers("/swagger/**","/swagger-ui.html", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**").permitAll()
            .anyRequest().authenticated();
    }
    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication()
        .withUser("admin").password("{noop}admin").roles("ADMIN")
        .and()
        .withUser("user").password("{noop}user").roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Puedes elegir el codificador de contraseñas adecuado para tu aplicación.
    }
    
}
