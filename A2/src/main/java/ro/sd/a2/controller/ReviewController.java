package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.dto.ReviewDTO;
import ro.sd.a2.dto.ReviewDetailsDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dtoValidator.ReviewDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.ProductReviewService;
import ro.sd.a2.service.UserAuthentication;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private static final Logger logger = Logger.getLogger(ReviewController.class.getName());

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     * Add a product review
     * @param reviewDTO data about new product review
     * @return a page containing corresponding product and his reviews. An error message will be
     * display if review cannot be added
     */
    @PostMapping("/saveReview")
    public ModelAndView saveProduct(ReviewDTO reviewDTO) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add a review to product " + reviewDTO.getIdProduct());

        ModelAndView mav = new ModelAndView();

        try {
            ReviewDTOValidator.validate(reviewDTO);

            productReviewService.add(reviewDTO);

            mav.setViewName("redirect:/product/" + reviewDTO.getIdProduct());
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add a review to product " + reviewDTO.getIdProduct() +
                    " " + e.getMessage());

            mav.addObject("exceptionReview", e.getMessage());
            mav.setViewName("redirect:/product/" + reviewDTO.getIdProduct());
        }

        return mav;
    }

    /**
     *
     * @return a page containing a product and his reviews
     */
    @GetMapping("/admin/viewProductReviews/{name}/{id_product}")
    public ModelAndView viewProductReviews(@PathVariable("name") String productName, @PathVariable("id_product") String id_product) {
        logger.log(Level.INFO, "/admin/viewProductReviews/" + productName +
                        "/" + id_product + " page accessed");

        List<ReviewDetailsDTO> reviews = productReviewService.findByProduct(id_product);
        ModelAndView mav = new ModelAndView();
        mav.addObject("productName", productName);
        mav.addObject("reviews", reviews);
        mav.setViewName("product_reviews");
        return mav;
    }

    /**
     * Delete product review with desired id (by admin)
     * @param id id of review to be deleted
     * @return a page containing corresponding product and his reviews
     */
    @GetMapping("/admin/deleteReview/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to delete review " + id);

        productReviewService.delete(id);

        return new ModelAndView("redirect:/admin/products");
    }

}
