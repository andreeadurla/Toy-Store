package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.Client;
import ro.sd.a2.entity.Product;
import ro.sd.a2.entity.ProductReview;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ReviewDTO {

    private String idProduct;

    private String idClient;

    private String review;

    public ProductReview toEntity() {

        String idReview = UUID.randomUUID().toString();

        ProductReview productReview = ProductReview.builder()
                .id(idReview)
                .date(new Date())
                .product(Product.builder().id(idProduct).build())
                .client(Client.builder().id(idClient).build())
                .review(review)
                .build();

        return productReview;
    }
}
