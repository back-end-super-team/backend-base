package backend.base.config.jpa;

import backend.base.entity.base.EntityId;
import com.github.f4b6a3.ulid.UlidCreator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class ULIDGenerator extends SequenceStyleGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        if (EntityId.class.isAssignableFrom(o.getClass()) && ((EntityId) o).getId() != null) {
            return ((EntityId) o).getId();
        }
        return UlidCreator.getUlid().toString();
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }

}
