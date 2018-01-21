package conference;

import ch.qos.logback.core.joran.action.ActionConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
//                .antMatchers("/schedule", "/signup", "/", "/login").permitAll()
                .antMatchers("/schedule", "/signup", "/greeting").permitAll()
                .antMatchers( "/user_presentation").hasAuthority("Administrator")
                .antMatchers( "/user_presentation").hasAuthority("Presenter")
                .antMatchers("/users").hasAuthority("Administrator")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/hello")
                .and()
                .csrf().disable()
            .logout()
                .permitAll();
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/schedule", "/signup", "/").permitAll()
//                .antMatchers( "/user_presentations").access("hasRole('USER')")
//                .antMatchers("/users").access("hasRole('ADMIN')")
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .csrf().disable()
//            .logout()
//                .permitAll();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("UserB").password("a").roles("USER")
//                .and()
//                .withUser("UserA").password("a").roles("USER")
//                .and()
//                .withUser("admin").password("a").roles("ADMIN");
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
