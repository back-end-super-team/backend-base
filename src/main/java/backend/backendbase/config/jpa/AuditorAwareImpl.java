package backend.backendbase.config.jpa;

import backend.backendbase.config.tenant.TenantContext;
import backend.backendbase.data.api.tenant.TokenData;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        TokenData tokenData = TenantContext.getTokenData();
        if (tokenData != null) {
            return Optional.of(tokenData.getId());
        }
        return Optional.of("SYSTEM");
    }

}
