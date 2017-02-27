package task.configs;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll();
        http.authorizeRequests()
                .antMatchers("/page").hasRole("user")
                .antMatchers("/lib").hasRole("librarian")
                .and()
                .formLogin()
                    .loginPage("/").failureUrl("/").permitAll()
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                            String role = auth.getAuthorities().toString();
                            if(role.contains("user")){
                                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/page");
                            } else if(role.contains("librarian")){
                                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/lib");
                            }
                            //System.out.println(role);
                        }
                    })
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // временно костыль :(
        auth.inMemoryAuthentication()
                .withUser("user").password("123").roles("user")
                .and()
                .withUser("lib").password("lib").roles("librarian");
    }
}
