package task.manager.task.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.task.model.Tasks;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Tasks, String> {
    Optional<Tasks> findByName(String taskName);

    @Query(value = "select * from tasks where created_by = ?1", nativeQuery = true)
    List<Tasks> findAllTasksByAssignedUser(String createdBy);
}
