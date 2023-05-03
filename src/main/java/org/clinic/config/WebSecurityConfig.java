package org.clinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity                                              // чтобы WebSecurity работал
@EnableGlobalMethodSecurity(prePostEnabled = true)              // разрешение проверки доступа к методам с помощью аннотаций !!!
@ComponentScan({
        "org.clinic.config",
        "org.clinic.service"})              // нужно для того, чтобы SS использовал US как AuthProvider (UserDetails)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userService;


    // Information encryptor function:
    // https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Тоже method chaining - схема доступа
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
            .and()                       // комбинируем условие
                .formLogin()            // у нас своя форма логина
                .loginPage("/sign-in")    // при sign in нажатие по кнопке перекидывает нас сюда - POST request!
                .defaultSuccessUrl("/main") // если пользователь вошел, то КУДА ОН ВОШЕЛ ???
                .permitAll()           // доступна для ВСЕХ
            .and()
                .logout()               // при выходе (написании этого пути)
                .logoutUrl("/sign-out") // куда нас перекинет (если мы просто напишем этот путь, то нас выкинет их системы)
                .permitAll()            // доступно всем!
                .logoutSuccessUrl("/sign-in"); // и снова на основную страницу
    }

    // Просим Spring установить UserDetailsService, а также PasswordEncoder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
