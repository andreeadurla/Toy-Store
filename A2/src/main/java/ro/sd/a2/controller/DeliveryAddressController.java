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
import ro.sd.a2.dto.DeliveryAddressDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dtoValidator.DeliveryAddressDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.DeliveryAddressService;
import ro.sd.a2.service.UserAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class DeliveryAddressController {

    private static final Logger logger = Logger.getLogger(DeliveryAddressController.class.getName());

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing all clients delivery addresses
     */
    @GetMapping("/delivery_address")
    public ModelAndView getDeliveryAddresses() {
        logger.log(Level.INFO, "/delivery_address page accessed");

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        List<DeliveryAddressDTO> deliveryAddressDTOs = deliveryAddressService.findByUserId(userDTO.getId());

        mav.addObject("delivery_addresses", deliveryAddressDTOs);
        mav.addObject("user", userDTO);

        mav.setViewName("delivery_address");
        return mav;
    }

    /**
     *
     * @param page the name of the previously accessed page
     * @return a form for adding a new delivery address
     */
    @GetMapping(value = {"/addAddressForm", "/addAddressForm/{page}"})
    public ModelAndView getAddAddressForm(@PathVariable(required = false) String page) {
        logger.log(Level.INFO, "/addAddressForm page accessed");

        ModelAndView mav = new ModelAndView();

        mav.addObject("user", userAuthentication.getAuthenticatedUser());
        mav.addObject("page", page);

        mav.setViewName("save_delivery_address");

        return mav;
    }

    /**
     *
     * @param id id of delivery address to be edited
     * @return a form for editing delivery address with desired id. Forms fields contain
     * current address details.
     */
    @GetMapping("/editAddressForm/{id}")
    public ModelAndView getEditAddressForm(@PathVariable("id") String id) {
        logger.log(Level.INFO, "/editAddressForm " + id + " page accessed");

        DeliveryAddressDTO deliveryAddressDTO = deliveryAddressService.find(id);

        ModelAndView mav = new ModelAndView();
        mav.addObject("delivery_address", deliveryAddressDTO);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("save_delivery_address");

        return mav;
    }

    /**
     * Add/edit a delivery address
     * @param page the name of the previously accessed page
     * @param deliveryAddressDTO new data about delivery address
     * @return if address was successfully created/updated and if previous page was "order"
     * then this method returns a order page. If previous page was something else, this method
     * returns a page containing all delivery addresses of authenticated user. Otherwise
     * if new data related to address contains id_delivery_address this method returns an edit form
     * containing initial details about address or an add form if id_delivery_address is null.
     */
    @PostMapping(value = {"/saveAddress", "/saveAddress/{page}"})
    public RedirectView saveAddress(RedirectAttributes redir,
                                    @PathVariable(required = false) String page, DeliveryAddressDTO deliveryAddressDTO) {

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                                " is trying to add/edit delivery address");

        RedirectView redirectView;

        try {
            DeliveryAddressDTOValidator.validate(deliveryAddressDTO);

            deliveryAddressService.add(deliveryAddressDTO);

            if(StringUtils.isEmpty(page))
                redirectView = new RedirectView("/delivery_address",true);
            else
                redirectView = new RedirectView("/placeOrder",true);
        }
        catch (InvalidDataException e) {

            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                                    " cannot add/edit delivery address " + deliveryAddressDTO.getId() +
                                    " " + e.getMessage());

            String id_delivery_address = deliveryAddressDTO.getId();

            if(StringUtils.isEmpty(id_delivery_address))
                redirectView = new RedirectView("/addAddressForm/" + page, true);
            else
                redirectView = new RedirectView("/editAddressForm/" + id_delivery_address, true);

            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     * Delete delivery address with desired id
     * @param id id of delivery address to be deleted
     * @return a page containing all clients delivery addresses
     */
    @GetMapping("/deleteAddress/{id}")
    public ModelAndView deleteAddress(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                                 " is trying to delete delivery address " + id);

        deliveryAddressService.delete(id);

        return new ModelAndView("redirect:/delivery_address");
    }

}
