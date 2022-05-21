package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.CategoryDTO;
import ro.sd.a2.dto.ProductDTO;
import ro.sd.a2.dto.ReviewDetailsDTO;
import ro.sd.a2.service.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @param id_category
     * @return a home page containing all products, or a home page containing products from
     * a certain category if id_category is not empty.
     */
    @GetMapping(value = {"/home", "/home/{id_category}"})
    public ModelAndView getAllProducts(@PathVariable(required = false) String id_category) {
        logger.log(Level.INFO, "/home/" + id_category + " page accessed");

        ModelAndView mav = new ModelAndView();

        List<ProductDTO> productDTOs;

        if(StringUtils.isEmpty(id_category))
            productDTOs = productService.findAll();
        else
            productDTOs = productService.findByCategory(id_category);

        mav.addObject("products", productDTOs);

        List<CategoryDTO> categoryDTOs = categoryService.findAll();
        mav.addObject("categories", categoryDTOs);

        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("home");

        return mav;
    }

    /**
     *
     * @param id_product id of product to be displayed
     * @return a page containing details about the selected product
     */
    @GetMapping("/product/{id_product}")
    public ModelAndView getProduct(@PathVariable("id_product") String id_product) {
        logger.log(Level.INFO, "/product/" + id_product + " page accessed");

        ProductDTO productDTO = productService.find(id_product);
        List<ReviewDetailsDTO> productReviews = productReviewService.findByProduct(id_product);

        ModelAndView mav = new ModelAndView();

        mav.addObject("product", productDTO);
        mav.addObject("reviews", productReviews);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("product");

        return mav;
    }
}
