package backend.base.component.repository;

import backend.base.component.ComponentTest;
import backend.base.repository.health.HealthRepositoryR4jWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ComponentTest
public class HealthRepositoryR4jWrapperComponentTest {

    @Autowired
    private HealthRepositoryR4jWrapper healthRepositoryR4jWrapper;

    @Test
    public void healthCheck_success() {
        assertTrue(healthRepositoryR4jWrapper.healthCheck());
    }

}
