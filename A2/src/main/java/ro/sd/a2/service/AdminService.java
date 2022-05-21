package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dto.LoginDTO;
import ro.sd.a2.dtoValidator.LoginDTOValidator;
import ro.sd.a2.entity.Admin;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.AdminRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(AdminService.class.getName());

    @Autowired
    private final AdminRepository adminRepository;

    /**
     * Find user which have specified login data (email and password)
     * @param loginDTO login data
     * @throws InvalidDataException if login data are invalid or if email or password
     * are wrong
     * @return an UserDTO containing necessary information about the found user
     */
    public UserDTO find(LoginDTO loginDTO) {

        LoginDTOValidator.validate(loginDTO);

        logger.log(Level.INFO, "Search admin " + loginDTO.getEmail());

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Admin admin = adminRepository.findByEmailAndPassword(email, password);

        if(ObjectUtils.isEmpty(admin))
            throw new InvalidDataException((WrongMessage.WRONG_EMAIL_OR_PASSWORD));

        logger.log(Level.INFO, "Admin " + loginDTO.getEmail() + " found");

        return UserDTO.fromEntity(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        logger.log(Level.INFO, "Search admin with email " + s);

        Admin admin = adminRepository.findByEmail(s);

        if(ObjectUtils.isEmpty(admin))
            throw new UsernameNotFoundException("Could not find admin with that email");

        return admin;
    }
}
