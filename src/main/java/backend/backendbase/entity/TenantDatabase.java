package backend.backendbase.entity;

import backend.backendbase.config.jpa.EncryptConverter;
import backend.backendbase.entity.base.EntityWithULID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tenant_database")
public class TenantDatabase extends EntityWithULID {

    @Size(max = 256)
    @NotNull
    @Column(name = "url", nullable = false, length = 256)
    private String url;

    @Size(max = 64)
    @NotNull
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Convert(converter = EncryptConverter.class)
    @Size(max = 256)
    @NotNull
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

}