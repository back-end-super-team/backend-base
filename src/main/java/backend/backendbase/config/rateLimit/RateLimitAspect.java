package backend.backendbase.config.rateLimit;

import backend.backendbase.annotation.RateLimited;
import backend.backendbase.enums.RateLimitType;
import backend.backendbase.exception.RateLimitExceededException;
import backend.backendbase.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimiterService rateLimiterService;

    @Around("@annotation(rateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        String key = generateKey(rateLimited.type());
        boolean allowed = rateLimiterService.tryConsume(
                key,
                rateLimited.capacity(),
                rateLimited.duration()
        );
        if (!allowed) {
            throw new RateLimitExceededException("Rate limit exceeded for " + rateLimited.type());
        }
        return joinPoint.proceed();
    }

    private String generateKey(RateLimitType type) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return switch (type) {
            case API -> request.getRequestURI();
            case USER -> {
                String userId = request.getHeader("X-User-Id"); // Assume user ID from header
                yield userId != null ? userId : "anonymous";
            }
            case IP -> request.getRemoteAddr();
            case FUNCTION -> "service:" + request.getRequestURI();
        };
    }

}
