package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Modifying
    @Query("select c from Category c where c.deleted = false")
    List<Category> findAll();

    Category findByName(String name);
}
