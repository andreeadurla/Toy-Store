package ro.sd.a2.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@SQLDelete(sql = "update Category c set c.deleted = true where c.id = ?")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    @PreRemove
    public void onPreRemove() {
        products.forEach(p -> p.setCategory(Category.builder().id("1").build()));
    }
}
