package com.crossent.microservice.culturalspacefront.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity      //스프링 시큐리티를 사용하겠다고 선언
@EnableGlobalAuthentication
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.admin.name:}")
    String adminName;

    @Value("${security.admin.password:}")
    String adminPassword;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    // 스프링 시큐리티 룰
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .csrf().disable()                   // csrf 방어 기능을 사용하지 않음. disable 설정하지 않을 경우, 로그인 폼에 csrf 토큰 hidden으로 설정해 줘야함
                .authorizeRequests()               // 각 경로에 따른 권한을 지정할 수 있음
                .antMatchers("/js/**","/index.html","/login.html", "/**").permitAll()       // 해당 파일들 권한 허용 (permitAll() : 모든 사용자 허용)
                .antMatchers("/api/facilities/**").hasRole("ADMIN")      // 해당 경로에는 ADMIN 권한을 가진 유저만 접근할 수 있다
                .anyRequest().authenticated()                                       // 그외의 모든 요청들은 인증된 사용자만 허용. ( authenticated() : 인증된 사용자 허용 )
                .and()
                    .formLogin()             // 로그인폼을 사용할 것임
                .and()
                    .logout()
                    .logoutSuccessUrl("/#!/main")       // 로그아웃 성공 시 이동 url
                .and()
                    .headers();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser(adminName).password(adminPassword).roles("ADMIN");
    }
    // 스프링 시큐리티 룰을 무시하게 하는 url 규칙
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}