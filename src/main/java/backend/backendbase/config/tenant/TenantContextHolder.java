package backend.backendbase.config.tenant;

import backend.backendbase.data.api.tenant.TokenData;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class TenantContextHolder {

    private static final InheritableThreadLocal<TokenData> contextThread = new InheritableThreadLocal<>();

    public static void setTokenData(TokenData tokenData) {
        contextThread.set(tokenData);
    }

    public static TokenData getTokenData() {
        return contextThread.get();
    }

    public static String getTenantId() {
        return getTokenData() == null ? null : getTokenData().getTenantId();
    }

    public static String getUserId() {
        return getTokenData() == null || getTokenData().getId() == null ? "anonymous" : getTokenData().getId();
    }

    public static String getRole() {
        return getTokenData() == null ? null : getTokenData().getRole();
    }

    public static void clear() {
        contextThread.remove();
    }

}
