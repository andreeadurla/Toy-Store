package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.dto.ClientDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dtoValidator.ClientDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.ClientService;
import ro.sd.a2.service.UserAuthentication;

import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing details about account of authenticated user
     */
    @GetMapping("/profile")
    public ModelAndView getUserDetails() {

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();
        String id_client = userDTO.getId();

        logger.log(Level.INFO, "/profile page accessed (user id = " + id_client + ")");

        ClientDTO clientDTO = clientService.findById(id_client);

        mav.addObject("user", userDTO);
        mav.addObject("user_details", clientDTO);
        mav.setViewName("profile");
        return mav;
    }

    /**
     * Update account details of authenticated user
     * @param clientDTO new account details
     * @return profile page if account details were successfully updated, otherwise a profile page
     * containing the corresponding error message.
     */
    @PostMapping("/saveProfileDetails")
    public RedirectView saveProfileDetails(RedirectAttributes redir,
                                           ClientDTO clientDTO) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to edit his profile");

        RedirectView redirectView;

        try {
            ClientDTOValidator.validate(clientDTO);

            clientService.editProfileDetails(clientDTO);

            redirectView = new RedirectView("/profile",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " cannot edit his profile "+
                    " " + e.getMessage());

            redirectView = new RedirectView("/profile",true);
            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }


}
