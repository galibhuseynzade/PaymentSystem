package paymentsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import paymentsystem.model.entity.CardEntity;
import paymentsystem.model.enums.CardStatus;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    Integer countByCustomerEntity_CustomerIdAndStatusIn(Integer customerId, List<CardStatus> cardStatusList);
    Page<CardEntity> findByCustomerEntity_CustomerIdAndStatusIn(Integer customerId, List<CardStatus> cardStatusList, Pageable pageable);
    Page<CardEntity> findByStatus(CardStatus status, Pageable pageable);
}
