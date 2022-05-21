package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.Product;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class UnsoldProductDTO {

    private String name;

    private float price;

    private int quantity;

    private String imageURL;

    private Date lastSale;

    public static UnsoldProductDTO fromEntity(Product product) {

        UnsoldProductDTO unsoldProductDTO = UnsoldProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .imageURL(product.getImageURL())
                .lastSale(product.getLastSale())
                .build();

        return unsoldProductDTO;
    }
}
