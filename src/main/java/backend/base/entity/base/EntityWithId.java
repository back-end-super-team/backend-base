package backend.base.entity.base;

import backend.base.annotation.ULID;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class EntityWithId implements EntityId {

    @Id
    @ULID
    @Size(max = 26)
    @Column(name = "id", nullable = false, length = 26)
    private String id;

}
