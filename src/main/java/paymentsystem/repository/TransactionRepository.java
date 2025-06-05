package paymentsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.entity.TransactionEntity;
import paymentsystem.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    Page<TransactionEntity> findByCustomerEntity_CustomerId(Integer customerId, Pageable pageable);
    List<TransactionEntity> findByStatus(TransactionStatus status);
    List<TransactionEntity> findByCustomerEntityAndTransactionDate(CustomerEntity customerEntity, LocalDate date);
    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.customerEntity.customerId = :customerId AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal getMonthlyTotalByCustomer(
            @Param("customerId") Integer customerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
