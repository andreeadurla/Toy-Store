package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.format.annotation.DateTimeFormat;
import ro.sd.a2.entity.Category;
import ro.sd.a2.entity.Product;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductDTO {

    private String id;

    private String name;

    private float price;

    private int quantity;

    private String description;

    private String category_id;

    private String category_name;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date lastSale;

    private String imageURL;

    public Product toEntity() {

        String id_product = id;

        if(ObjectUtils.isEmpty(id))
            id_product = UUID.randomUUID().toString();

        Product product = Product.builder()
                                .id(id_product)
                                .name(name)
                                .price(price)
                                .quantity(quantity)
                                .description(description)
                                .category(Category.builder().id(category_id).build())
                                .lastSale(lastSale)
                                .deleted(false)
                                .imageURL(imageURL)
                                .build();

        return product;
    }

    public static ProductDTO fromEntity(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                                        .id(product.getId())
                                        .name(product.getName())
                                        .price(product.getPrice())
                                        .quantity(product.getQuantity())
                                        .category_id(product.getCategory().getId())
                                        .category_name(product.getCategory().getName())
                                        .description(product.getDescription())
                                        .imageURL(product.getImageURL())
                                        .lastSale(product.getLastSale())
                                        .build();

        return productDTO;
    }

}
