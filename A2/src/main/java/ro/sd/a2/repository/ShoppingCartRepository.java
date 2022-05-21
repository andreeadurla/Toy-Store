package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.ShoppingCartItem;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartItem, String> {

    List<ShoppingCartItem> findByClient_id(String client_id);

    ShoppingCartItem findByClient_idAndProduct_id(String client_id, String product_id);

}
