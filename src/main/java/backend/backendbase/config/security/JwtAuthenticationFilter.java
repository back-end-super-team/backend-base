package backend.backendbase.config.security;

import backend.backendbase.config.tenant.TenantContext;
import backend.backendbase.config.tenant.TenantManager;
import backend.backendbase.data.UserData;
import backend.backendbase.data.api.tenant.TokenData;
import backend.backendbase.service.JwtService;
import backend.backendbase.utility.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            tokenData.getUsername(), null, null
                    );
                    authToken.setDetails(tokenData);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    TenantContext.setTokenData(tokenData);
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
