package paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paymentsystem.model.entity.AccountEntity;
import paymentsystem.model.enums.AccountStatus;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    List<AccountEntity> findByCustomerEntity_CustomerIdAndStatusIn(Integer customerId, List<AccountStatus> accountStatusList);
    List<AccountEntity> findByStatus(AccountStatus status);
    List<AccountEntity> findByCustomerEntity_CustomerIdAndStatus(Integer customerId, AccountStatus status);
}
