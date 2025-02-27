package backend.backendbase.repository;

import backend.backendbase.entity.TenantUser;

import java.util.Optional;

public interface TenantUserRepository extends BaseRepository<TenantUser> {

    Optional<TenantUser> findByUsername(String username);

}
