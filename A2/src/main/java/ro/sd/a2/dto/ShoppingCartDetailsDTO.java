package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.ShoppingCartItem;

@Data
@Builder
@AllArgsConstructor
public class ShoppingCartDetailsDTO {

    private String id;

    private int quantity;

    private String client_id;

    private String product_id;

    private String product_name;

    private String product_imageURL;

    private float price;


    public static ShoppingCartDetailsDTO fromEntity(ShoppingCartItem shoppingCartItem) {

        float price = shoppingCartItem.getQuantity() * shoppingCartItem.getProductPrice();
        ShoppingCartDetailsDTO shoppingCartItemDTO = ShoppingCartDetailsDTO.builder()
                                                        .id(shoppingCartItem.getId())
                                                        .quantity(shoppingCartItem.getQuantity())
                                                        .product_id(shoppingCartItem.getProductId())
                                                        .product_name(shoppingCartItem.getProductName())
                                                        .product_imageURL(shoppingCartItem.getProductImageURL())
                                                        .price(price)
                                                        .build();

        return shoppingCartItemDTO;
    }

}
