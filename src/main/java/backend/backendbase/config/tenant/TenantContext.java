package backend.backendbase.config.tenant;

import backend.backendbase.data.api.tenant.TokenData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class TenantContext {

    private static final InheritableThreadLocal<TokenData> contextThread = new InheritableThreadLocal<>();

    private TenantContext() {
    }
    public static void setTokenData(TokenData userInfo) {
        contextThread.set(userInfo);
    }

    public static TokenData getTokenData() {
        try {
            return (TokenData) SecurityContextHolder.getContext().getAuthentication().getDetails();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String getTenantId() {
        return getTokenData() == null ? null : getTokenData().getTenantId();
    }

    public static void clear() {
        contextThread.remove();
    }

}
