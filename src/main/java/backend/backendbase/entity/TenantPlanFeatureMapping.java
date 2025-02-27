package backend.backendbase.entity;

import backend.backendbase.entity.base.EntityWithAware;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tenant_plan_feature_mapping")
public class TenantPlanFeatureMapping extends EntityWithAware {

    @EmbeddedId
    private TenantPlanFeatureMappingKey key;

}