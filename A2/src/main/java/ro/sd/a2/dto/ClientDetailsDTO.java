package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.AccountStatus;
import ro.sd.a2.entity.Client;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ClientDetailsDTO {

    private String id;

    private Date creationDate;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private AccountStatus status;

    public static ClientDetailsDTO fromEntity(Client client) {

        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTO.builder()
                .id(client.getId())
                .creationDate(client.getCreationDate())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .status(client.getStatus())
                .build();

        return clientDetailsDTO;
    }

}
