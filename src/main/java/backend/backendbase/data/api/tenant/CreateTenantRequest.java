package backend.backendbase.data.api.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateTenantRequest(
        @NotEmpty @Size(max = 64)
        String tenantName,
        @NotEmpty @Size(max = 128)
        String firstName,
        @NotEmpty @Size(max = 128)
        String lastName,
        @NotEmpty @Size(max = 4)
        String internationalCallingCode,
        @NotEmpty @Size(max = 20)
        String phoneNumber,
        @NotEmpty @Size(max = 256) @Email
        String email,
        @NotEmpty @Size(max = 64)
        String identificationNumber
) {}
