package ro.sd.a2.dtoValidator;

import ro.sd.a2.dto.ReviewDTO;
import ro.sd.a2.validator.Validator;

public class ReviewDTOValidator {

    public static void validate(ReviewDTO reviewDTO) {

        Validator.isNotEmpty(reviewDTO.getIdClient());
        Validator.isNotEmpty(reviewDTO.getIdProduct());
        Validator.isNotEmpty(reviewDTO.getReview());
    }


}
