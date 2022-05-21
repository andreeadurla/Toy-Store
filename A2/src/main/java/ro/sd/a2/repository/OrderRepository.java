package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.ClientOrder;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ClientOrder, String> {

    List<ClientOrder> findByClientId(String clientId);

}

