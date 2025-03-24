package backend.base.config.security;

import backend.base.config.tenant.TenantContextHolder;
import backend.base.config.tenant.TenantManager;
import backend.base.data.UserData;
import backend.base.data.api.tenant.TokenData;
import backend.base.service.JwtService;
import backend.base.utility.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.HashMap;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final TenantManager tenantManager;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        try {
            final String authHeader = request.getHeader(Constant.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith(Constant.BEARER)) {
                final String jwt = authHeader.substring(Constant.BEARER.length());
                if (jwtService.isTokenValid(jwt)) {
                    Claims claims = jwtService.extractAllClaims(jwt);
                    TokenData tokenData = objectMapper.convertValue(new HashMap<>(claims), UserData.class);
                    TenantContextHolder.setTokenData(tokenData);
                    tenantManager.setCurrentTenant(tokenData.getTenantId());
                }
            }
            LocaleContextHolder.setLocale(request.getLocale());
            filterChain.doFilter(request, response);
        }
        catch (Exception exception) {
            log.error("Unknown exception, message = {}", exception.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}
