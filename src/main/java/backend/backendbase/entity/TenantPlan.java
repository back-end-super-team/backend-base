package backend.backendbase.entity;

import backend.backendbase.entity.base.EntityWithULID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tenant_plan")
public class TenantPlan extends EntityWithULID {

    @Size(max = 128)
    @NotNull
    @Column(name = "plan_name", nullable = false, length = 128)
    private String planName;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    @NotNull
    @Column(name = "max_admin", nullable = false)
    private Long maxAdmin;

    @NotNull
    @Column(name = "max_request_per_day", nullable = false)
    private Long maxRequestPerDay;

    @NotNull
    @Column(name = "max_product", nullable = false)
    private Long maxProduct;

    @Column(name = "description")
    private String description;

}