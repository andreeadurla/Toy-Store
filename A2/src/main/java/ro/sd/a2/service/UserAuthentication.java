package ro.sd.a2.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.entity.User;

@Component
public class UserAuthentication {

    public UserDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails) {
            return UserDTO.fromEntity((User)principal);
        }

        return null;
    }
}
