package backend.base.data;

import backend.base.data.api.tenant.TokenData;
import lombok.Data;

@Data
public class UserData implements TokenData {

    private String username;
    private String password;
    private String id;
    private String role;
    private String tenantId;

}
