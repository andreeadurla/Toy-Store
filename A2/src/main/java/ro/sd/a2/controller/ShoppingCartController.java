package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.service.ShoppingCart;
import ro.sd.a2.dto.ShoppingCartDetailsDTO;
import ro.sd.a2.dto.ShoppingCartItemDTO;
import ro.sd.a2.dtoValidator.ShoppingCartItemDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.ShoppingCartService;
import ro.sd.a2.service.UserAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private static final Logger logger = Logger.getLogger(ShoppingCartController.class.getName());

    @Autowired
    private ShoppingCartService shopping_cartService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing all shopping cart items of authenticated user
     */
    @GetMapping("/cart")
    public ModelAndView getAllItems() {
        logger.log(Level.INFO, "/cart page accessed");

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        List<ShoppingCartDetailsDTO> shoppingCartDetailsDTOs = shopping_cartService.findByUserId(userDTO.getId());
        float price = ShoppingCart.computeTotalPrice(shoppingCartDetailsDTOs);

        mav.addObject("price", price);
        mav.addObject("items", shoppingCartDetailsDTOs);
        mav.addObject("user", userDTO);

        mav.setViewName("cart");

        return mav;
    }

    /**
     * Add only a product to the shopping cart
     * @param id_product id of product to be edited
     * @return home page without error messages if product was successfully added, otherwise
     * error messages will be displayed
     */
    @GetMapping("/addAProduct/{id_product}")
    public ModelAndView addAProduct(@PathVariable("id_product") String id_product) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add product " + id_product + " to shopping cart");

        ShoppingCartItemDTO shoppingCartItemDTO = ShoppingCartItemDTO.builder()
                .quantity(1)
                .client_id(userDTO.getId())
                .product_id(id_product)
                .build();

        ModelAndView mav = new ModelAndView();

        try {
            shopping_cartService.add(shoppingCartItemDTO);
            mav.setViewName("redirect:/home");
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add product " + shoppingCartItemDTO.getProduct_id() +
                    " to shopping cart " + e.getMessage());

            mav.addObject("exception", e.getMessage());
            mav.setViewName("redirect:/home");
        }

        return mav;
    }

    /**
     * Add a new product to the shopping cart. This product can have the desired quantity.
     * @param shoppingCartItemDTO data about new shopping cart item
     * @return home page if product was successfully added, otherwise a page containing the wanted product
     * and the error message.
     */
    @PostMapping("/addProduct")
    public RedirectView addProduct(HttpServletRequest request, RedirectAttributes redir,
                                   ShoppingCartItemDTO shoppingCartItemDTO) {

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add product " + shoppingCartItemDTO.getProduct_id() +
                " to shopping cart");

        RedirectView redirectView;

        String id = userDTO.getId();

        shoppingCartItemDTO.setClient_id(id);

        try {
            ShoppingCartItemDTOValidator.validate(shoppingCartItemDTO);
            shopping_cartService.add(shoppingCartItemDTO);

            redirectView = new RedirectView("/home",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getId() +
                    " cannot add product " + shoppingCartItemDTO.getProduct_id() +
                    " to shopping cart " + e.getMessage());

            redirectView = new RedirectView("/product/" + shoppingCartItemDTO.getProduct_id(), true);
            redir.addFlashAttribute("exception", e.getMessage());
        }


        return redirectView;
    }

    /**
     * Update quantity of a shopping cart item
     * @param id id of shopping cart item to be edited
     * @param quantity desired quantity
     * @return cart page without error messages if product was successfully added, otherwise
     * error messages will be displayed
     */
    @PostMapping("/updateQuantity")
    public RedirectView saveProduct(RedirectAttributes redir,
                                    String id, int quantity) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to update quantity of shopping cart item " + id);

        RedirectView redirectView;

        try {
            shopping_cartService.updateQuantity(id, quantity);
            redirectView = new RedirectView("/cart",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot update quantity of shopping cart item " + id +
                    " " + e.getMessage());

            redirectView = new RedirectView("/cart",true);
            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     * Delete shopping cart item with desired id
     * @param id id of shopping cart item to be deleted
     * @return cart page containing all remaining items of authenticated user
     */
    @GetMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to delete item " + id + "from shopping cart");

        shopping_cartService.delete(id);

        return new ModelAndView("redirect:/cart");
    }

}
