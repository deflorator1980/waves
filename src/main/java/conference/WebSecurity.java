package conference;

import ch.qos.logback.core.joran.action.ActionConst;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/schedule", "/signup", "/").permitAll()
                .antMatchers( "/user_presentations").access("hasRole('USER')")
                .antMatchers("/users").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .csrf().disable()
            .logout()
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("UserB").password("a").roles("USER")
                .and()
                .withUser("UserA").password("a").roles("USER")
                .and()
                .withUser("admin").password("a").roles("ADMIN");

    }
}
