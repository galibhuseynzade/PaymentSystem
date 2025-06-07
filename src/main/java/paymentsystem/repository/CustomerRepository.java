package paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paymentsystem.model.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
