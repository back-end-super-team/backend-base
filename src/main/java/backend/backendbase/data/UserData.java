package backend.backendbase.data;

import backend.backendbase.data.api.tenant.TokenData;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserData implements UserDetails, TokenData {

    private String username;
    private String password;
    private String id;
    private String roleId;
    private String tenantId;
    private Collection<? extends GrantedAuthority> authorities;

}
