package com.orwellg.bifrost.login.configuration;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LoginConfig extends WebSecurityConfigurerAdapter {

  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
*/

        @Autowired
        private Environment environment;

        private final String PROFIlE_ACTIVE_SWAGGER = "sid";

        private static final Logger LOG = LogManager.getLogger(LoginConfig.class);

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            String[] profiles = this.environment.getActiveProfiles();

            String profile = null;
            if (profiles == null || profiles.length < 1) {
                LOG.error("Error retrieving profiles. Active profile not found. Setting to local");
                profile = "local";
            } else {
                LOG.info("Retrieved active profiles: {}", new Gson().toJson(profiles));
                profile = profiles[0];
            }

            LOG.info("Active profile: {}", profile);

            if (PROFIlE_ACTIVE_SWAGGER.equals(profile)) {
                LOG.info("Profile {} active. Allowing swagger", profile);
                httpSecurity
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeRequests()
                        .antMatchers(
                                HttpMethod.POST,
                                "/",
                                "/**/*.html",
                                "/**/*.{png,jpg,jpeg,svg.ico}",
                                "/**/*.css",
                                "/**/*.js"
                        ).permitAll()
                        .antMatchers("/bifrost/login/**").permitAll()
                        .antMatchers("/swagger-resources/**").permitAll()
                        .antMatchers("/v2/**").permitAll()
                        .anyRequest().authenticated();

            } else {
                LOG.info("Profile {} active. Disabling swagger: {}", profile);
                httpSecurity
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeRequests()
                        .antMatchers(
                                HttpMethod.POST,
                                "/",
                                "/**/*.html",
                                "/**/*.{png,jpg,jpeg,svg.ico}",
                                "/**/*.css",
                                "/**/*.js"
                        ).permitAll()
                        .antMatchers("/bifrost/login/**").permitAll()
                        .antMatchers("/swagger-resources/**").denyAll()
                        .antMatchers("/v2/**").denyAll()
                        .anyRequest().authenticated();
            }

            httpSecurity.headers().cacheControl();
        }
    }
