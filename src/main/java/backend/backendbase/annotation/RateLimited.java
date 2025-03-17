package backend.backendbase.annotation;

import backend.backendbase.enums.RateLimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RateLimited {
    RateLimitType type();           // Type of limit
    int capacity();                 // Number of tokens
    long duration();                // Duration in seconds
}
