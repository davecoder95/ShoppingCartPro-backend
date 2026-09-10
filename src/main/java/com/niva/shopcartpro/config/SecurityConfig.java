package com.niva.shopcartpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    /*
     * JwtAuthenticationFilter is our custom filter.
     *
     * It checks incoming requests for:
     *
     * Authorization: Bearer <JWT>
     *
     * If a valid JWT is found, the filter authenticates
     * the user and stores the authentication information
     * in Spring Security's SecurityContext.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
     * Spring uses constructor injection to provide our
     * JwtAuthenticationFilter bean.
     *
     * JwtAuthenticationFilter is annotated with @Component,
     * so Spring automatically creates it and can inject it here.
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*
     * This method creates the main Spring Security filter chain.
     *
     * Spring Security uses this filter chain to decide:
     *
     * 1. Which requests are public
     * 2. Which requests require authentication
     * 3. Which authentication mechanisms are enabled
     * 4. Which security filters should run
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        /*
         * Disable CSRF protection.
         *
         * Our application is currently a REST API.
         * We are using JWT/HTTP authentication rather than
         * browser-based session authentication.
         *
         * CSRF protection can be configured differently later
         * if we build a browser-based application.
         */
        http
                .csrf(csrf -> csrf.disable())

                /*
                 * Define authorization rules for our endpoints.
                 *
                 * permitAll()
                 * -> no authentication required
                 *
                 * authenticated()
                 * -> user must be authenticated
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * Login must be public.
                         *
                         * A user cannot provide a JWT before logging in
                         * because the JWT is created during login.
                         */
                        .requestMatchers("/auth/**").permitAll()

                        /*
                         * Product endpoints are currently public.
                         */
                        .requestMatchers("/products/**").permitAll()

                        /*
                         * User endpoints are currently public.
                         *
                         * Role-based restrictions will be added later.
                         */
                        .requestMatchers("/users/**").permitAll()

                        /*
                         * Order endpoints require authentication.
                         *
                         * Later, the JWT filter will authenticate the
                         * request before Spring reaches this authorization rule.
                         */
                        .requestMatchers("/orders/**").authenticated()

                        /*
                         * Any endpoint not explicitly listed above
                         * requires authentication.
                         */
                        .anyRequest().authenticated())

                /*
                 * Enable HTTP Basic authentication temporarily.
                 *
                 * We are keeping this while developing JWT so that
                 * we don't change too many things at once.
                 *
                 * Later, when JWT is fully working, we will remove
                 * HTTP Basic authentication.
                 */
                // .httpBasic(Customizer.withDefaults())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /*
                 * Add our JWT filter before Spring Security's
                 * UsernamePasswordAuthenticationFilter.
                 *
                 * This means our filter gets an opportunity to read
                 * and validate the JWT before Spring performs its
                 * normal username/password authentication processing.
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        /*
         * Return the completed Spring Security filter chain.
         */
        return http.build();
    }

    /*
     * PasswordEncoder bean.
     *
     * BCrypt is used to hash user passwords before they are
     * stored in the database.
     *
     * Example:
     *
     * test123
     * ↓
     * BCryptPasswordEncoder
     * ↓
     * $2a$10$....
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
     * AuthenticationManager bean.
     *
     * AuthenticationManager is responsible for processing
     * authentication requests.
     *
     * Our AuthController calls:
     *
     * authenticationManager.authenticate(...)
     *
     * Spring then delegates the authentication to the
     * appropriate AuthenticationProvider.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        /*
         * AuthenticationConfiguration provides the
         * AuthenticationManager configured by Spring Security.
         *
         * Spring already knows about our UserDetailsService
         * and PasswordEncoder beans, so it can build the
         * authentication system for us.
         */
        return configuration.getAuthenticationManager();
    }
}
