package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    private String id;

    @Column(name = "unit_price", nullable = false)
    private float unit_price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false)
    private float total_price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClientOrder client_order;

    public static OrderItem convertFrom(String orderId, ShoppingCartItem shoppingCartItem) {

        String id = UUID.randomUUID().toString();

        OrderItem orderItem = OrderItem.builder()
                .id(id)
                .unit_price(shoppingCartItem.getProductPrice())
                .quantity(shoppingCartItem.getQuantity())
                .unit_price(shoppingCartItem.getProductPrice())
                .total_price(shoppingCartItem.getQuantity() * shoppingCartItem.getProductPrice())
                .product(shoppingCartItem.getProduct())
                .client_order(ClientOrder.builder().id(orderId).build())
                .build();

        return orderItem;
    }

    public String getProductImageURL() {
        return product.getImageURL();
    }

    public String getProductName() {
        return product.getName();
    }
}
