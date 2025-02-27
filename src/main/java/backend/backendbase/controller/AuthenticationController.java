package backend.backendbase.controller;

import backend.backendbase.data.api.ApiResponse;
import backend.backendbase.data.api.login.LoginRequest;
import backend.backendbase.data.api.login.LoginResponse;
import backend.backendbase.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {

    private AuthenticationService authenticationService;

    @SneakyThrows
    @PostMapping("/v1/login")
    public ApiResponse authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        String jwtToken = authenticationService.loginAndGenerateToken(loginRequest);
        return ApiResponse.ok(new LoginResponse(jwtToken));
    }

}
