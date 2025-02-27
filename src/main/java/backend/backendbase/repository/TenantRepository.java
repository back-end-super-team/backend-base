package backend.backendbase.repository;

import backend.backendbase.entity.Tenant;
import backend.backendbase.enums.TenantStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends BaseRepository<Tenant> {

    @Modifying
    @Query("update Tenant set status = :status where id = :tenantId")
    int updateTenantStatus(String tenantId, TenantStatus status);

}
