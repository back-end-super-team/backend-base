package backend.backendbase.entity;

import backend.backendbase.entity.base.EntityWithULID;
import backend.backendbase.enums.TenantStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tenant")
public class Tenant extends EntityWithULID {

    @Size(max = 64)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TenantStatus status;

    @Column(name = "tenant_plan_id")
    private String tenantPlanId;

}