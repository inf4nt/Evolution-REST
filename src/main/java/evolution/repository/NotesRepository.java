package evolution.repository;

import evolution.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "notes", path = "notes")
public interface NotesRepository extends JpaRepository<Notes, Long> {
}
