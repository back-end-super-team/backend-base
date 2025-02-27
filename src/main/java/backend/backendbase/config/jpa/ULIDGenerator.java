package backend.backendbase.config.jpa;

import backend.backendbase.entity.base.EntityWithULID;
import com.github.f4b6a3.ulid.UlidCreator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class ULIDGenerator extends SequenceStyleGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        if (EntityWithULID.class.isAssignableFrom(o.getClass()) && ((EntityWithULID) o).getId() != null) {
            return ((EntityWithULID) o).getId();
        }
        return UlidCreator.getUlid().toString();
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }

}
