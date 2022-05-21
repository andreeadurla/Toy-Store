package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Product;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Query("select p from Product p where p.deleted = false and p.quantity > 0")
    List<Product> findAll();

    @Modifying
    @Query("select p from Product p where p.deleted = false")
    List<Product> findAllAdmin();

    @Modifying
    @Query("select p from Product p where p.deleted = false and p.category.id = ?1 and p.quantity > 0")
    List<Product> findByCategoryId(String categoryId);

    Product findByName(String name);

    @Modifying
    @Query("select p from Product p where p.deleted = false and (p.lastSale <= ?1 or p.lastSale IS NULL)")
    List<Product> findByLastSaleBefore(Date date);
}
