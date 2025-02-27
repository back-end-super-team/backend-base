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
@Table(name = "tenant_feature")
public class TenantFeature extends EntityWithULID {

    @Size(max = 128)
    @NotNull
    @Column(name = "feature_name", nullable = false, length = 128)
    private String featureName;

}