package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.CreditCard;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

    boolean existsByNoCard(String noCard);

    List<CreditCard> findByClientId(String clientId);
}
