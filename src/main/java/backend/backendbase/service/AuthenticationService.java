package backend.backendbase.service;

import backend.backendbase.data.api.login.LoginRequest;
import backend.backendbase.data.api.tenant.TokenData;
import backend.backendbase.utility.Constant;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthenticationService extends BaseService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String loginAndGenerateToken(LoginRequest loginRequest) {
        Authentication authenticatedUser = authenticate(loginRequest);
        Map<String, Object> claims = authentication2claims(authenticatedUser);
        return jwtService.generateToken(claims);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        String loginIdentityString = String.join(Constant.SEPARATE, loginRequest.tenantId(), loginRequest.username());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginIdentityString,
                        loginRequest.password()
                )
        );
    }

    private Map<String, Object> authentication2claims(Authentication authentication) {
        TokenData tokenData = (TokenData) authentication.getPrincipal();
        Map<String, Object> mapTokenData = this.objectMapper.convertValue(tokenData, new TypeReference<>() {});
        mapTokenData.remove(Constant.PASSWORD);
        return mapTokenData;
    }

}
