package com.example.ganglng.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (Stateless한 REST API에서는 보통 비활성화합니다)
                .csrf(csrf -> csrf.disable())
                // 요청 경로에 대한 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        // "/api/**" 경로로 오는 모든 요청은 인증 없이 허용
                        .requestMatchers("/api/**").permitAll()
                        // 그 외 모든 요청은 인증을 요구 (나중에 필요시 설정)
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
