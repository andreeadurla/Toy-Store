package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.sd.a2.entity.AccountStatus;
import ro.sd.a2.entity.Client;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Client toEntity() {

        String id = UUID.randomUUID().toString();

        Client client = Client.builder()
                            .id(id)
                            .creationDate(new Date())
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .phone(phone)
                            .password(bCryptPasswordEncoder.encode(password))
                            .status(AccountStatus.ENABLE)
                            .build();

        return client;
    }

}
