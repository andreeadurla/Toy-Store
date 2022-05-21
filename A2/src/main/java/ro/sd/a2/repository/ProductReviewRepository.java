package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Product;
import ro.sd.a2.entity.ProductReview;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, String> {

   List<ProductReview> findByProductId(String productId);
}
