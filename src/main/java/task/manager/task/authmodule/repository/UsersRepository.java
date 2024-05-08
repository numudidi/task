package task.manager.task.authmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.manager.task.authmodule.model.UserEntity;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntitiesByEmail(String email);
}
