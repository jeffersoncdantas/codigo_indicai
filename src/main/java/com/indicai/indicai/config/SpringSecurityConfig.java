package com.indicai.indicai.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.indicai.indicai.jwt.JwtAuthorizationFilter;



@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/filmes").permitAll()
                        .requestMatchers("/resources/**", "/scripts/**", "/styles/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                //.formLogin(form -> form.defaultSuccessUrl("/index.html", true))
                .formLogin((form) ->
                                form
                                .defaultSuccessUrl("/index")
                                .permitAll()
                        )
                .build();
    }

    @Bean
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));// url da qual as requisições devem ser autorizadas
                                                            // http://localhost:8080
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));// métdos autorizados
        configuration.applyPermitDefaultValues();// autoriza os métodos
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}