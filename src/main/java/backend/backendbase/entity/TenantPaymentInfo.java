package backend.backendbase.entity;

import backend.backendbase.entity.base.EntityWithULID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tenant_payment_info")
public class TenantPaymentInfo extends EntityWithULID {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

}