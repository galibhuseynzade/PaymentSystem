package paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paymentsystem.model.entity.CardEntity;
import paymentsystem.model.enums.CardStatus;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, String> {
    List<CardEntity> findByCustomerEntity_CustomerIdAndStatusIn(Integer customerId, List<CardStatus> cardStatusList);
    List<CardEntity> findByStatus(CardStatus status);
}
