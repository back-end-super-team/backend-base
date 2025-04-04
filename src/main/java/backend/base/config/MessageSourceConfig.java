package backend.base.config;

import backend.base.enums.ResponseEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MessageSourceConfig {

    private final MessageSource messageSource;

    @PostConstruct
    public void init() {
        ResponseEnum.setMessageSource(messageSource);
    }

}
