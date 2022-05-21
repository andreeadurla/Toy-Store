package ro.sd.a2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ro.sd.a2.service.AdminService;
import ro.sd.a2.service.ClientService;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private AdminService adminService;

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(adminService).passwordEncoder(bCryptPasswordEncoder());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/admin/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasAuthority("ADMIN")
                    
                    .and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/validateAccount")
                    .failureUrl("/admin/login?error=true")
                    .defaultSuccessUrl("/admin/products", true)
                    .permitAll()

                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login")
                    .deleteCookies("JSESSIONID")
                    .permitAll()

                    .and()
                    .csrf().disable();
        }
    }

    @Configuration
    @Order(2)
    public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private ClientService clientService;

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(clientService).passwordEncoder(bCryptPasswordEncoder());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/home/**").permitAll()
                    .antMatchers("/product/**").permitAll()
                    .anyRequest().hasAuthority("USER")

                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/validateAccount")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()

                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home")
                    .deleteCookies("JSESSIONID")
                    .permitAll()

                    .and()
                    .csrf().disable();
        }
    }
}
