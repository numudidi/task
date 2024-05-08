package task.manager.task.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.task.model.TaskAssignment;

import java.util.List;

@Repository
public interface AssignmentRepository extends CrudRepository<TaskAssignment, String> {
    @Query("select a from TaskAssignment a where a.userId = ?1")
    List<TaskAssignment> findAllAssignmentsByUserId(String userId);
}
