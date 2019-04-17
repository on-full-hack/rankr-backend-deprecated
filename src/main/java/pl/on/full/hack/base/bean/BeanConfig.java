package pl.on.full.hack.base.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
