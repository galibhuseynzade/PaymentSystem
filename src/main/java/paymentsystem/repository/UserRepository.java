package paymentsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import paymentsystem.model.entity.UserEntity;
import paymentsystem.model.enums.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Page<UserEntity> findByStatus(UserStatus status, Pageable pageable);
}
