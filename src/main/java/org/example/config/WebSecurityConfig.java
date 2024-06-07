package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.config.auth.MyLoginSuccessHandler;
import org.example.config.auth.MyLogoutSuccessHandler;
import org.example.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    // 로그인한 유저들만 접근 가능한 URL


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserRepository userRepository) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/boards/write", "/boards/{category}/{boardId}/edit", "/boards/{category}/{boardId}/delete", "/likes/**", "/users/my-page", "/users/edit", "/users/delete").authenticated()
                        .anyRequest().permitAll())
                // anyRequest - requestMatchers 에서 지정된 url 외의 요청에 대한 설정
                // authenticated - 해당 url 에 집입하기 위해서는 인증이 필요함
                .formLogin((login) -> login
                        .loginPage("/users/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(new MyLoginSuccessHandler(userRepository))
                        .defaultSuccessUrl("/boards/list")
                        .failureUrl("/users/login?fail"))
                .logout((logout) -> logout
                        .logoutUrl("/users/logout")
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler()));


        return http.build();
    }
}
