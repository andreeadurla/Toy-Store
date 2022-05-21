package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import ro.sd.a2.entity.Client;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ClientDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    public static ClientDTO fromEntity(Client client) {

        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();

        return clientDTO;
    }

}
