package backend.backendbase.service;

import backend.backendbase.data.UserData;
import backend.backendbase.entity.TenantUser;
import backend.backendbase.repository.TenantUserRepository;
import backend.backendbase.utility.Constant;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService extends BaseService implements UserDetailsService {

    private TenantUserRepository tenantUserRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String loginIdentityString) throws UsernameNotFoundException {
        String[] loginIdentityStringSplit = loginIdentityString.split(Constant.SEPARATE);
        String tenantId = loginIdentityStringSplit[0];
        String username = loginIdentityStringSplit[1];
        if ("null".equals(tenantId) || tenantId.isEmpty()) {
            TenantUser tenantUser = tenantUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            return modelMapper.map(tenantUser, UserData.class);
        }
        else {
            return null;
        }
    }

}
