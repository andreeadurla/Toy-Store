package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {

    @Id
    private String id;

    @Column(name = "review", nullable = false)
    private String review;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public String getClientName() {
        return client.getFullName();
    }

}
