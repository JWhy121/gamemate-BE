package com.example.gamemate.global.config;

import com.example.gamemate.domain.auth.jwt.JWTUtil;
import com.example.gamemate.domain.auth.jwt.filter.JWTFilter;
import com.example.gamemate.domain.auth.jwt.filter.LoginFilter;
import com.example.gamemate.domain.user.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguration 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    //JWTUtil 주입
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;

    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http.csrf(AbstractHttpConfigurer::disable)

            //Form 로그인 방식 disable
            .formLogin(AbstractHttpConfigurer::disable)

            //http basic 인증 방식 disable
            .httpBasic(AbstractHttpConfigurer::disable)

            //경로별 인가 작업
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/", "/join").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll() // Swagger 경로 허용
                .anyRequest().authenticated())

            //JWTFilter 등록
            .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)

            //필터 추가
            //AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함
            //AuthenticationManager()와 JWTUtil, customUserDetailsService, bCryptPasswordEncoder() 인수 전달
            .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, customUserDetailsService, bCryptPasswordEncoder()), UsernamePasswordAuthenticationFilter.class)

            //세션 설정
            //JWT를 통한 인증/인가를 위해 세션을 STATELESS 상태로 설정
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            //oauth2
            .oauth2Login(Customizer.withDefaults())

            //cors 설정
            .cors((cors -> cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);

                    configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                    return configuration;
                }
            })));

        return http.build();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/friend/**").permitAll() // /friend/** 경로에 대한 접근 허용
                        .anyRequest().authenticated() // 다른 모든 요청은 인증 필요
                )
                .httpBasic(Customizer.withDefaults()); // 기본 인증 방식 사용
        return http.build();
    }
}

