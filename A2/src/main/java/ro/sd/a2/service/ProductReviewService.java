package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.ProductDTO;
import ro.sd.a2.dto.ReviewDTO;
import ro.sd.a2.dto.ReviewDetailsDTO;
import ro.sd.a2.dtoValidator.ReviewDTOValidator;
import ro.sd.a2.entity.ProductReview;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.repository.ProductReviewRepository;
import ro.sd.a2.validator.Validator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private static final Logger logger = Logger.getLogger(ProductReviewService.class.getName());

    @Autowired
    private final ProductReviewRepository productReviewRepository;

    /**
     * Add a new product review
     * @param reviewDTO data necessary for new product review
     * @throws InvalidDataException if review's data are invalid
     */
    public void add(ReviewDTO reviewDTO) {

        ReviewDTOValidator.validate(reviewDTO);

        ProductReview productReview = reviewDTO.toEntity();
        productReviewRepository.save(productReview);

        logger.log(Level.INFO, "Review of client " + reviewDTO.getIdClient() + " successfully added");
    }

    /**
     * Find all reviews of the product with specified id
     * @param idProduct id of product to which the reviews belong
     * @throws InvalidDataException if product id is invalid
     * @return a list of ReviewDetailsDTO which contains necessary information about the product reviews
     */
    public List<ReviewDetailsDTO> findByProduct(String idProduct) {

        Validator.isNotEmpty(idProduct);

        logger.log(Level.INFO, "Search all reviews corresponding to" +
                        "product " + idProduct);

        List<ProductReview> productReview = productReviewRepository.findByProductId(idProduct);

        List<ReviewDetailsDTO> reviews = productReview.stream()
                .map(r -> ReviewDetailsDTO.fromEntity(r))
                .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all reviews corresponding to" +
                        "product " + idProduct);

        return reviews;
    }

    /**
     * Delete review with specified id
     * @param id id of review to be deleted
     * @throws InvalidDataException if review id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        productReviewRepository.deleteById(id);

        logger.log(Level.INFO, "Review with id " + id + " successfully deleted");
    }

}
