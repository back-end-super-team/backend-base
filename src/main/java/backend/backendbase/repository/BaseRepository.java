package backend.backendbase.repository;

import backend.backendbase.entity.base.EntityWithID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends EntityWithID> extends JpaRepository<T, String> {
}
