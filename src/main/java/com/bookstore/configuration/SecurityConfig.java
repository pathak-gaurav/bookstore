package com.bookstore.configuration;

import com.bookstore.service.UserSecurityService;
import com.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
/*EnableGlobalMethodSecurity provides AOP security on methods,some of annotation it will enable are
PreAuthorize PostAuthorize also it has support for JSR-250.*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private UserSecurityService userSecurityService;

    private BCryptPasswordEncoder passwordEncoder() {
        return SecurityUtility.passwordEncoder();
    }

    public static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/fonts/**",
            "/image/**",
            "/js/**",
            "/index",
            "/forgetPassword",
            "/newUser",
            "/login",
            "/"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .authorizeRequests()
              .antMatchers(PUBLIC_MATCHERS)
              .permitAll().anyRequest().authenticated()
              .and()
              .csrf().disable().cors().disable()
                     //failureUrl() – the landing page after an unsuccessful login
              .formLogin().failureUrl("/login?error")
                    //defaultSuccessUrl() – the landing page after a successful login
              .defaultSuccessUrl("/")
              .loginPage("/login").permitAll()
              .and()

              /* Matcher which compares a pre-defined ant-style pattern against the URL ( servletPath + pathInfo) of an
                HttpServletRequest. The query string of the URL is ignored and matching is case-insensitive or
                case-sensitive depending on the arguments passed into the constructor.
                Using a pattern value of /** or ** is treated as a universal match, which will match any request.

                Patterns which end with /** (and have no other wildcards) are optimized by using a substring
                match — a pattern of /aaa/** will match /aaa, /aaa/ and any sub-directories, such as /aaa/bbb/ccc.
                For all other cases, Spring's AntPathMatcher is used to perform the match.
                */

              .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
              .and()
              .rememberMe();
    }
}

