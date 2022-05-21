package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.sd.a2.dto.*;
import ro.sd.a2.dtoValidator.ClientDTOValidator;
import ro.sd.a2.dtoValidator.LoginDTOValidator;
import ro.sd.a2.dtoValidator.RegisterDTOValidator;
import ro.sd.a2.entity.AccountStatus;
import ro.sd.a2.entity.Client;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.ClientRepository;
import ro.sd.a2.validator.Validator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Find user which have specified login data (email and password)
     * @param loginDTO login data
     * @throws InvalidDataException if login data are invalid, or if email or password
     * are wrong, of if user account is disable
     * @return an UserDTO containing necessary information about the found user
     */
    public UserDTO find(LoginDTO loginDTO) {

        LoginDTOValidator.validate(loginDTO);

        logger.log(Level.INFO, "Search client " + loginDTO.getEmail());

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Client client = clientRepository.findByEmailAndPassword(email, password);

        if(ObjectUtils.isEmpty(client))
            throw new InvalidDataException(WrongMessage.WRONG_EMAIL_OR_PASSWORD);

        AccountStatus status = client.getStatus();
        if(status.equals(AccountStatus.DISABLE))
            throw new InvalidDataException(WrongMessage.WRONG_STATUS);

        logger.log(Level.INFO, "Client " + loginDTO.getEmail() + " found");

        return UserDTO.fromEntity(client);
    }

    /**
     * Find user with specified id
     * @param id id of user to be found
     * @throws InvalidDataException if user id is invalid
     * @return a ClientDTO containing necessary information about the found user
     */
    public ClientDTO findById(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search user with id " + id);

        Client client = clientRepository.getOne(id);

        if(ObjectUtils.isEmpty(client)) {
            logger.log(Level.INFO, "User with id " + id + " not found");
            return null;
        }

        logger.log(Level.INFO, "User with id " + id + " found");

        return ClientDTO.fromEntity(client);
    }

    /**
     * Add a new user. If doesn't exists an user which already use specified email address and
     * if a phone number is specified and it isn't already used by another user, then a new user
     * account is created.
     * @param registerDTO registration data
     * @throws InvalidDataException if registration data are invalid or if already exists an account
     * which use specified email address or specified phone number
     * @return an UserDTO containing necessary information about the new user
     */
    public UserDTO add(RegisterDTO registerDTO) {

        RegisterDTOValidator.validate(registerDTO);

        String email = registerDTO.getEmail();

        boolean existsEmail = clientRepository.existsByEmail(email);

        if(BooleanUtils.isTrue(existsEmail))
            throw new InvalidDataException(WrongMessage.WRONG_EMAIL);

        String phone = registerDTO.getPhone();

        if(!StringUtils.isEmpty(phone)) {
            boolean existsPhone = clientRepository.existsByPhone(phone);

            if (BooleanUtils.isTrue(existsPhone))
                throw new InvalidDataException((WrongMessage.WRONG_PHONE));
        }

        Client client = clientRepository.save(registerDTO.toEntity());

        logger.log(Level.INFO, "Account for " + client.getEmail() + " created successfully");

        return UserDTO.fromEntity(client);
    }

    /**
     * Edit user profile (firstname, lastname and phone number)
     * @param clientDTO new information about user account
     * @throws InvalidDataException if specified data are invalid
     */
    public void editProfileDetails(ClientDTO clientDTO) {

        ClientDTOValidator.validate(clientDTO);

        String id = clientDTO.getId();
        String firstname = clientDTO.getFirstName();
        String lastname = clientDTO.getLastName();
        String phone = clientDTO.getPhone();

        Client client = clientRepository.getOne(id);

        if(ObjectUtils.isEmpty(client)) {
            logger.log(Level.INFO, "User with id " + id + " not found");
            return ;
        }

        client.setFirstName(firstname);
        client.setLastName(lastname);
        client.setPhone(phone);

        clientRepository.save(client);

        logger.log(Level.INFO, client.getEmail() + "profile edited successfully");
    }

    /**
     * Find all clients
     * @return a list of ClientDetailsDTO which contains details about the registered clients
     */
    public List<ClientDetailsDTO> findAll() {

        logger.log(Level.INFO, "Search all clients");

        List<Client> clients = clientRepository.findAll();

        List<ClientDetailsDTO> clientsDTOs =  clients.stream()
                                                    .map(c -> ClientDetailsDTO.fromEntity(c))
                                                    .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all clients");

        return clientsDTOs;
    }

    /**
     * Edit client account status
     * @param id id of client account to be edited
     * @param status new account status
     * @throws InvalidDataException if specified id is invalid
     */
    public void updateStatus(String id, AccountStatus status) {

        Validator.isNotEmpty(id);

        Client client = clientRepository.getOne(id);

        if(ObjectUtils.isEmpty(client)) {
            logger.log(Level.INFO, "User with id " + id + " not found");
            return ;
        }

        client.setStatus(status);

        clientRepository.save(client);

        logger.log(Level.INFO, client.getEmail() + " account's status was successfully edited" +
                                    "; STATUS = " + status);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        logger.log(Level.INFO, "Search client with email " + s);

        Client client = clientRepository.findByEmail(s);

        if(ObjectUtils.isEmpty(client))
            throw new UsernameNotFoundException("Could not find client with that email");

        return client;
    }
}
