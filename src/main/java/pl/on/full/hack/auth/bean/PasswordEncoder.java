package pl.on.full.hack.auth.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    @Bean
    public BCryptPasswordEncoder get() {
        return new BCryptPasswordEncoder();
    }
}
