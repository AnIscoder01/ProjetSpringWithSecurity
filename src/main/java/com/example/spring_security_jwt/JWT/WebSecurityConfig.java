package com.example.spring_security_jwt.JWT;

import com.example.spring_security_jwt.Entity.ERole;
import com.example.spring_security_jwt.Service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig {
@Autowired
    UserDetailsServiceImp userDetailsServiceImp;
@Autowired
    private AuthEntryPointJwt unauthorizedHandler;
@Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
}
@Bean
public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsServiceImp);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;


}

@Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig)
        throws Exception {
            return authConfig.getAuthenticationManager();
}

@Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/salledesport/**").permitAll()
            .requestMatchers("/abonnement/**").permitAll()
            .requestMatchers("/admin/**","/salledesport/**","/client/**","/owner/**").hasRole("ADMIN")
//            .requestMatchers("/client/**").permitAll()
            .requestMatchers("/api/test/**").permitAll()
            .anyRequest().authenticated();
    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authenticationJwtTokenFilter(),
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
    }





}
