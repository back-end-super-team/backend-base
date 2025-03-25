package backend.base.component.repository;

import backend.base.component.ComponentTest;
import backend.base.repository.health.HealthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ComponentTest
public class HealthRepositoryComponentTest {

    @Autowired
    private HealthRepository healthRepository;

    @Test
    public void healthCheck_success() {
        assertTrue(healthRepository.healthCheck());
    }

}
