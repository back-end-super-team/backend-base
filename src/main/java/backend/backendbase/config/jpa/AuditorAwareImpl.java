package backend.backendbase.config.jpa;

import backend.backendbase.config.tenant.TenantContextHolder;
import backend.backendbase.data.api.tenant.TokenData;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Value("${config.master-ulid}")
    private String masterUlid;

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        TokenData tokenData = TenantContextHolder.getTokenData();
        if (tokenData != null) {
            return Optional.of(tokenData.getId());
        }
        return Optional.of(masterUlid);
    }

}
