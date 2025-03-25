package backend.base.repository.health;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HealthRepository {

    private final EntityManager entityManager;

    public boolean healthCheck() {
        return entityManager.unwrap(Session.class).isConnected();
    }

}
