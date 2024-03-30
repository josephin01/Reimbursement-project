package com.reimbursement.project.config;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.filter.JwtAuthFilter;
import com.reimbursement.project.repository.EmployeeDetailsRepository;
import com.reimbursement.project.repository.service.impl.EmployeeDetailsRepoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final EmployeeDetailsRepository employeeDetailsRepository;

    private static final String[] ADMIN_URLS = {"/admin/**","/api/v1/batches/**","/api/v1/admin/**","/api/v1/roles","/api/v1/employees","/api/v1/employees/","/api/v1/employees/allemployees","/api/v1/employees/id/**","/api/v1/employees/delete/**","/api/v1/expense-claims/admin-view/**","/api/v1/expense-claims/admin/view","/api/v1/expenses/getbyrole/**","/api/v1/notification/admin","/api/v1/projects","/api/v1/projects/delete/**","/api/v1/travelForm/getTravelFormByRole/**","/api/v1/travelForm/admin/**"};
    private static final String[] MANAGER_URLS = {"/api/v1/manager/view/**","/api/v1/notification/**","/api/v1/travelForm/manager/**"};


    @Bean
    public UserDetailsService userDetailsService() {
        return new EmployeeDetailsRepoServiceImpl(employeeDetailsRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(configurationSource()))
//                .authorizeHttpRequests(req -> {
//                    req.requestMatchers("/api/v1/auth/**").permitAll();
//                    req.requestMatchers(MANAGER_URLS).hasAuthority(Constant.MANAGER);
//                    req.requestMatchers(ADMIN_URLS).hasAuthority(Constant.ADMIN);
//                    req.anyRequest().authenticated();
//                })

                .authorizeHttpRequests(req ->
                    req.anyRequest().permitAll()
                )
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }
}
