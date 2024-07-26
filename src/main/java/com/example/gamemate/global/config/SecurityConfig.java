package com.example.gamemate.global.config;

import com.example.gamemate.domain.user.UserRole;
import com.example.gamemate.global.auth.JwtTokenFilter;
import com.example.gamemate.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private static final String secretKey = "my-secret-key-123123";

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/jwt-login/info").authenticated()
                .requestMatchers("/jwt-login/admin/**").hasAuthority(UserRole.ADMIN.name())
                .anyRequest().permitAll())
            .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//            .csrf().disable() // CSRF 비활성화
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
//            .and()
//            .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/jwt-login/info").authenticated() // 인증 필요
//                .requestMatchers("/jwt-login/admin/**").hasAuthority(UserRole.ADMIN.name()) // ADMIN 권한 필요
//                .anyRequest().permitAll() // 그 외 요청은 허용
//            );
//
//        return httpSecurity.build();
//
//        return httpSecurity
//            .httpBasic().disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
//            .authorizeRequests()
//            .requestMatchers("/jwt-login/info").authenticated()
//            .requestMatchers("/jwt-login/admin/**").hasAuthority(UserRole.ADMIN.name())
//            .and().build();
//    }
}
