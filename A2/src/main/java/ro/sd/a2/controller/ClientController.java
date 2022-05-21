package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.ClientDetailsDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.entity.AccountStatus;
import ro.sd.a2.service.ClientService;
import ro.sd.a2.service.UserAuthentication;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger logger = Logger.getLogger(ClientController.class.getName());

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing all clients accounts
     */
    @GetMapping("/clients")
    public ModelAndView getAllClients() {
        logger.log(Level.INFO, "/admin/categories page accessed");

        ModelAndView mav = new ModelAndView();

        List<ClientDetailsDTO> clientDetailsDTOs = clientService.findAll();

        mav.addObject("clients", clientDetailsDTOs);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("clients");
        return mav;
    }

    /**
     * Update the status of the account that has desired id.
     * @param id id of client to be edited
     * @param status new account status
     * @return a page containing all clients accounts
     */
    @GetMapping("/editClientStatus/{id}/{status}")
    public ModelAndView editClientStatus(@PathVariable("id") String id, @PathVariable("status") AccountStatus status) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to edit client " + id + " status");

        clientService.updateStatus(id, status);

        return new ModelAndView("redirect:/admin/clients");
    }

}
