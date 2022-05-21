package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.ProductReview;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ReviewDetailsDTO {

    private String id;

    private String review;

    private Date date;

    private String clientName;

    public static ReviewDetailsDTO fromEntity(ProductReview productReview) {

        ReviewDetailsDTO reviewDetailsDTO = ReviewDetailsDTO.builder()
                .id(productReview.getId())
                .review(productReview.getReview())
                .date(productReview.getDate())
                .clientName(productReview.getClientName())
                .build();

        return reviewDetailsDTO;
    }

}
