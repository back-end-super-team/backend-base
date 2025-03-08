package backend.backendbase.common.result;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceInjector {

    private final MessageSource messageSource;

    @PostConstruct
    public void init() {
        ResultCode.setMessageSource(messageSource);
    }
}