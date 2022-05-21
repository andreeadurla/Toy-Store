package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.OrderItem;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    @Modifying
    @Query("select i from OrderItem i where i.client_order.date between ?1 and ?2")
    List<OrderItem> findAllByDate(Date startDate, Date endDate);

}
