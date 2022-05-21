package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.CreditCardDTO;
import ro.sd.a2.dto.CreditCardDetailsDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dtoValidator.CreditCardDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.CreditCardService;
import ro.sd.a2.service.UserAuthentication;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class CreditCardController {

    private static final Logger logger = Logger.getLogger(CreditCardController.class.getName());

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing all credits cards of authenticated client
     */
    @GetMapping("/credit_cards")
    public ModelAndView getCreditCards() {
        logger.log(Level.INFO, "/credit_cards page accessed");

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        List<CreditCardDetailsDTO> creditCardDetailsDTOs = creditCardService.findByUserId(userDTO.getId());

        mav.addObject("credit_cards", creditCardDetailsDTOs);
        mav.addObject("user", userDTO);

        mav.setViewName("credit_cards");
        return mav;
    }

    /**
     *
     * @return a form for adding a new credit card
     */
    @GetMapping("/addCreditCardForm")
    public ModelAndView getAddCreditCardForm() {
        logger.log(Level.INFO, "/admin/addCreditCardForm page accessed");

        ModelAndView mav = new ModelAndView();

        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("save_credit_card");

        return mav;
    }

    /**
     * Add a credit card
     * @param creditCardDTO data about the new credit card
     * @return a page containing all credit cards of authenticated user if credit card was
     * successfully created. Otherwise a page containing the form for adding a new card and
     * the corresponding error.
     */
    @PostMapping("/saveCreditCard")
    public ModelAndView saveCreditCard(CreditCardDTO creditCardDTO) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add a credit card");

        ModelAndView mav = new ModelAndView();

        try {

            CreditCardDTOValidator.validate(creditCardDTO);

            creditCardService.add(creditCardDTO);

            mav.setViewName("redirect:/credit_cards");
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add a credit card" + e.getMessage());

            mav = getAddCreditCardForm();
            mav.addObject("exception", e.getMessage());
        }

        return mav;
    }

    /**
     * Delete credit card with desired id
     * @param id id of credit card to be deleted
     * @return a page containing all credit cards of authenticated user
     */
    @GetMapping("/deleteCreditCard/{id}")
    public ModelAndView deleteCreditCard(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to delete credit card " + id);

        creditCardService.delete(id);

        return new ModelAndView("redirect:/credit_cards");
    }
}
