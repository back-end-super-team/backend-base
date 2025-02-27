package backend.backendbase.repository;

import backend.backendbase.entity.base.EntityWithULID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends EntityWithULID> extends JpaRepository<T, String> {
}
