package account.security;

import account.errors.RestAuthenticationEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static account.RoleRepo.*;

@Configuration
@EnableWebSecurity
public class WebConfigureImpl extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // manage access
                .mvcMatchers("/api/auth/signup").permitAll()
                .mvcMatchers("/api/auth/changepass").hasAnyAuthority(USER.getAuthority(),
                        ACCOUNTANT.getAuthority(), ADMIN.getAuthority())
                .mvcMatchers("/api/admin/**").hasAuthority(ADMIN.getAuthority())
                .mvcMatchers("/api/empl/payment").hasAnyAuthority(ACCOUNTANT.getAuthority(),
                        USER.getAuthority())
                .mvcMatchers("/api/acct/**").hasAuthority(ACCOUNTANT.getAuthority())
                .anyRequest().permitAll() //register and others
                .and()
                .httpBasic()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

}