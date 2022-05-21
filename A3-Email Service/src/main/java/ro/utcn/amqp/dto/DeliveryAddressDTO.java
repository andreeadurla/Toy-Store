package ro.utcn.amqp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DeliveryAddressDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String county;

    private String city;

    private String address;

    private String phone;

    private String id_client;

}
