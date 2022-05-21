package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItem {

    @Id
    private String id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "deleted")
    private boolean deleted;

    public String getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }

    public float getProductPrice() {
        return product.getPrice();
    }

    public int getProductQuantity() {
        return product.getQuantity();
    }

    public String getProductImageURL() {
        return product.getImageURL();
    }
}
