package backend.backendbase.entity;

import backend.backendbase.entity.base.EntityWithULID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tenant_info")
public class TenantInfo extends EntityWithULID {

    @Size(max = 128)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 128)
    private String firstName;

    @Size(max = 128)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName;

    @Size(max = 4)
    @NotNull
    @Column(name = "international_calling_code", nullable = false, length = 4)
    private String internationalCallingCode;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 256)
    @NotNull
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Size(max = 20)
    @NotNull
    @Column(name = "identification_number", nullable = false, length = 20)
    private String identificationNumber;

    @Size(max = 256)
    @NotNull
    @Column(name = "identification_card_front", nullable = false, length = 256)
    private String identificationCardFront;

    @Size(max = 256)
    @Column(name = "identification_card_back", length = 256)
    private String identificationCardBack;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

}