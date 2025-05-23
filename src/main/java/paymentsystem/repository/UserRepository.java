package paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paymentsystem.model.entity.UserEntity;
import paymentsystem.model.enums.UserStatus;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findByStatus(UserStatus status);
}
