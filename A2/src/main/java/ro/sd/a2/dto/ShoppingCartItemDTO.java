package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.Client;
import ro.sd.a2.entity.Product;
import ro.sd.a2.entity.ShoppingCartItem;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ShoppingCartItemDTO {

    private int quantity;

    private String client_id;

    private String product_id;

    public ShoppingCartItem toEntity() {

        String id = UUID.randomUUID().toString();

        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                                                                .id(id)
                                                                .quantity(quantity)
                                                                .client(Client.builder().id(client_id).build())
                                                                .product(Product.builder().id(product_id).build())
                                                                .build();

        return shoppingCartItem;
    }

}
