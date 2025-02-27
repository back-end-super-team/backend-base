package backend.backendbase.entity;

import backend.backendbase.entity.base.EntityWithULID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tenant_user")
public class TenantUser extends EntityWithULID {

    @NotNull
    @Column(name = "tenant_id")
    private String tenantId;

    @Size(max = 64)
    @NotNull
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Size(max = 256)
    @NotNull
    @Column(name = "password", nullable = false, length = 256)
    private String password;

}