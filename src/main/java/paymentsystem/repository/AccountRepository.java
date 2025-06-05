package paymentsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paymentsystem.model.entity.AccountEntity;
import paymentsystem.model.enums.AccountStatus;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    Integer countByCustomerEntity_CustomerIdAndStatusIn(Integer customerId, List<AccountStatus> accountStatusList);
    List<AccountEntity> findByCustomerEntity_CustomerIdAndStatus(Integer customerId, AccountStatus accountStatus);
    Page<AccountEntity> findByCustomerEntity_CustomerIdAndStatusIn(Integer customerId, List<AccountStatus> accountStatusList, Pageable pageable);
    Page<AccountEntity> findByStatus(AccountStatus status, Pageable pageable);
    Page<AccountEntity> findByCustomerEntity_CustomerIdAndStatus(Integer customerId, AccountStatus status, Pageable pageable);
}
