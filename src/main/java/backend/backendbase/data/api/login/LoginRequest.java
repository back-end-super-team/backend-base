package backend.backendbase.data.api.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @Size(max = 64)
        @NotBlank
        String username,

        @Size(max = 256)
        @NotBlank
        String password,

        @Size(max = 26)
        String tenantId
) {}
