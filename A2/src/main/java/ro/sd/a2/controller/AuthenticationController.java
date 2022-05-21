package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.MessageDTO;
import ro.sd.a2.dto.RegisterDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dtoValidator.RegisterDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.ClientService;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @return user's login page
     */
    @GetMapping("/login")
    public ModelAndView getClientLoginPage() {
        logger.log(Level.INFO, "/login page accessed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("login_client");
        return mav;
    }

    /**
     *
     * @return admin's login page
     */
    @GetMapping("/admin/login")
    public ModelAndView getAdminLoginPage() {

        logger.log(Level.INFO, "/admin/login page accessed");

        ModelAndView mav = new ModelAndView();

        mav.setViewName("login_admin");
        return mav;
    }

    /**
     *
     * @return user's register page
     */
    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        logger.log(Level.INFO, "/register page accessed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("register_client");
        return mav;
    }

    /**
     * Creates a user account using data entered by user
     * @param registerDTO data needed to create an account
     * @return user's home page if user has successfully created an account, otherwise register page containing
     * the corresponding error message.
     */
    @PostMapping("/register")
    public ModelAndView addClient(RegisterDTO registerDTO) {

        logger.log(Level.INFO, "Try to create user account");

        ModelAndView mav = new ModelAndView();

        try {
            RegisterDTOValidator.validate(registerDTO);

            UserDTO userDTO = clientService.add(registerDTO);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            requestHeaders.setBearerAuth("37452b9d-f19d-4b93-95fd-360e958a4bc2" + "89c41799-3125-4432-955c-d576fee11687");

            HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO, requestHeaders);

            ResponseEntity<MessageDTO> responseEntity = restTemplate.exchange("http://localhost:8081/email/generateEmail",
                                                                                HttpMethod.POST, requestEntity, MessageDTO.class);

            if(responseEntity.getStatusCode().is2xxSuccessful())
                logger.log(Level.INFO, "Success: " + responseEntity.getBody());

            mav.setViewName("redirect:/login");
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Cannot create user account: " + e.getMessage());

            mav.addObject("exception", e.getMessage());
            mav.setViewName("register_client");
        }
        catch(HttpClientErrorException e) {
            logger.log(Level.INFO, "Error: " + e.getMessage());
            mav.setViewName("redirect:/login");
        }

        return mav;
    }

    /**
     * Validates the login data entered by user
     * @param loginDTO login data (email and password)
     * @return user's home page if user has successfully logged in, otherwise login page containing
     * the corresponding error message.
     */
    /*@PostMapping("/validateAccount")
    public ModelAndView validateClientAccount(LoginDTO loginDTO) {
        logger.log(Level.INFO, "Client: " + loginDTO.getEmail() + " is trying to log in");

        ModelAndView mav = new ModelAndView();

        try {
            LoginDTOValidator.validate(loginDTO);

            userDTO = clientService.find(loginDTO);

            mav.setViewName("redirect:/home");

            logger.log(Level.INFO, "Client: " + loginDTO.getEmail() + " logged in successfully");
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client " + loginDTO.getEmail() + " cannot log in: " + e.getMessage());

            mav.addObject("exception", e.getMessage());
            mav.setViewName("login_client");
        }

        return mav;
    }*/

    /**
     * Validates the login data entered by admin
     * @param loginDTO login data (email and password)
     * @return admin's home page if admin has successfully logged in, otherwise login page containing
     * the corresponding error message.
     */
    /*@PostMapping("/admin/validateAccount")
    public ModelAndView validateAdminAccount(LoginDTO loginDTO) {

        logger.log(Level.INFO, "Admin: " + loginDTO.getEmail() + " is trying to log in");

        ModelAndView mav = new ModelAndView();

        try {
            LoginDTOValidator.validate(loginDTO);

            userDTO = adminService.find(loginDTO);

            mav.setViewName("redirect:/admin/products");

            logger.log(Level.INFO, "Admin: " + loginDTO.getEmail() + " logged in successfully");
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Admin " + loginDTO.getEmail() + " cannot log in: " + e.getMessage());

            mav.addObject("exception", e.getMessage());
            mav.setViewName("login_admin");
        }

        return mav;
    }*/

    /**
     * User logout (authenticated user becomes null)
     * @return user's home page
     */
    /*@GetMapping("/logout")
    public ModelAndView logout() {

        logger.log(Level.INFO, "User " + AuthenticationController.userDTO.getEmail() + " has logged out");

        userDTO = null;

        return new ModelAndView("redirect:/home");
    }*/

    /**
     * Admin logout (authenticated admin becomes null)
     * @return admin's home page
     */
    /*@GetMapping("/admin/logout")
    public ModelAndView logoutAdmin() {

        logger.log(Level.INFO, "Admin " + AuthenticationController.userDTO.getEmail() + " has logged out");

        userDTO = null;

        return new ModelAndView("redirect:/admin/login");
    }*/

}
