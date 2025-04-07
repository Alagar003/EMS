//    package com.example.demo.config;
//
//    import com.example.demo.utils.JwtUtil;
//    import org.springframework.context.annotation.Bean;
//    import org.springframework.context.annotation.Configuration;
//    import org.springframework.security.authentication.AuthenticationManager;
//    import org.springframework.security.authentication.ProviderManager;
//    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//    import org.springframework.security.core.userdetails.UserDetailsService;
//    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//    import org.springframework.security.web.SecurityFilterChain;
//    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//    import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
//    import org.springframework.web.cors.CorsConfiguration;
//    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//    import org.springframework.web.filter.CorsFilter;
//    import org.springframework.web.servlet.config.annotation.CorsRegistry;
//    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//    @Configuration
//    @EnableWebSecurity
//    public class SecurityConfig {
//        private final JwtUtil jwtUtil;
//        private final UserDetailsService userDetailsService;
//
//        public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//            this.jwtUtil = jwtUtil;
//            this.userDetailsService = userDetailsService;
//        }
//
//        @Bean
//        public BCryptPasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Bean
//        public AuthenticationManager authenticationManager() {
//            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//            authProvider.setUserDetailsService(userDetailsService);
//            authProvider.setPasswordEncoder(passwordEncoder());
//            return new ProviderManager(authProvider);
//        }
//
//    //    @Bean
//    //    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    //        http
//    //                .csrf(csrf -> csrf.disable())
//    //                .authorizeHttpRequests(auth -> auth
//    ////                        .requestMatchers("/admin/register", "/admin/login").permitAll()
//    //                                .requestMatchers("/admin/register", "/admin/login", "admin/employee/login").permitAll()
//    //                                .requestMatchers("/admin/create-employee").hasAuthority("ROLE_ADMIN")
//    //                        .requestMatchers("/employee/**").hasAuthority("ROLE_EMPLOYEE")
//    //                        .anyRequest().authenticated()
//    //                )
//    //                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//    //
//    //        return http.build();
//    //    }
//
//
//
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable())
//                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers(
//                                    "/admin/register",
//                                    "/admin/login",
//                                    "/admin/employee/login",
//                                    "/forgotPassword/changePassword/**",
//                                    "forgotPassword/verifyMail/**",
//                                    "forgotPassword/verifyOtp/**",
//                                    "admin/update-/**"
//                            ).permitAll()
//                            .requestMatchers("/admin/create-employee").hasAuthority("ROLE_ADMIN")
//                            .requestMatchers("/employee/**").hasAuthority("ROLE_EMPLOYEE")
//    //                        .requestMatchers("/forgotPassword/**").permitAll()
//                            .anyRequest().authenticated()
//                    )
//                    .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//
//            return http.build();
//        }
//
//
//
//
//        public class CorsConfig {
//            @Bean
//            public WebMvcConfigurer corsConfigurer() {
//                return new WebMvcConfigurer() {
//                    @Override
//                    public void addCorsMappings(CorsRegistry registry) {
//                        registry.addMapping("/**")
//                                .allowedOrigins("http://localhost:3000")
//                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                                .allowedHeaders("*")
//                                .allowCredentials(true);
//                    }
//                };
//            }
//        }
//    }
//
//    //
//    //package com.example.demo.config;
//    //
//    //import com.example.demo.utils.JwtUtil;
//    //import org.springframework.context.annotation.Bean;
//    //import org.springframework.context.annotation.Configuration;
//    //import org.springframework.http.HttpMethod;
//    //import org.springframework.security.authentication.AuthenticationManager;
//    //import org.springframework.security.authentication.ProviderManager;
//    //import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//    //import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//    //import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//    //import org.springframework.security.core.userdetails.UserDetailsService;
//    //import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//    //import org.springframework.security.web.SecurityFilterChain;
//    //import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//    //import org.springframework.web.cors.CorsConfiguration;
//    //import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//    //import org.springframework.web.filter.CorsFilter;
//    //import org.springframework.web.servlet.config.annotation.CorsRegistry;
//    //import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//    //
//    //import java.util.List;
//    //
//    //@Configuration
//    //@EnableWebSecurity
//    //public class SecurityConfig {
//    //    private final JwtUtil jwtUtil;
//    //    private final UserDetailsService userDetailsService;
//    //
//    //    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//    //        this.jwtUtil = jwtUtil;
//    //        this.userDetailsService = userDetailsService;
//    //    }
//    //
//    //    @Bean
//    //    public BCryptPasswordEncoder passwordEncoder() {
//    //        return new BCryptPasswordEncoder();
//    //    }
//    //
//    //    @Bean
//    //    public AuthenticationManager authenticationManager() {
//    //        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    //        authProvider.setUserDetailsService(userDetailsService);
//    //        authProvider.setPasswordEncoder(passwordEncoder());
//    //        return new ProviderManager(authProvider);
//    //    }
//    //
//    //    @Bean
//    //    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    //        http
//    //                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
//    //                .csrf(csrf -> csrf.disable())
//    //                .authorizeHttpRequests(auth -> auth
//    //                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//    //                        .requestMatchers("/admin/register", "/admin/login", "/employee/login").permitAll()
//    //                        .requestMatchers("/admin/create-employee").hasAuthority("ROLE_ADMIN")
//    //                        .requestMatchers("/employee/**").hasAuthority("ROLE_EMPLOYEE")
//    //                        .anyRequest().authenticated()
//    //                )
//    //                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//    //
//    //        return http.build();
//    //    }
//    //
//    //    @Bean
//    //    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//    //        CorsConfiguration configuration = new CorsConfiguration();
//    //        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//    //        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//    //        configuration.setAllowedHeaders(List.of("*"));
//    //        configuration.setAllowCredentials(true);
//    //
//    //        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    //        source.registerCorsConfiguration("/**", configuration);
//    //        return source;
//    //    }
//    //}


package com.example.demo.config;

import com.example.demo.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Enable CORS here
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ Allow preflight
                        .requestMatchers(
                                "/admin/register",
                                "/admin/login",
                                "/admin/employee/login",
                                "/admin/allemployees",
                                "/forgotPassword/changePassword/**",
                                "/forgotPassword/verifyMail/**",
                                "/forgotPassword/verifyOtp/**",
                                "/admin/update-**"
                        ).permitAll()
                        .requestMatchers("/admin/create-employee").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/employee/**").hasAuthority("ROLE_EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
