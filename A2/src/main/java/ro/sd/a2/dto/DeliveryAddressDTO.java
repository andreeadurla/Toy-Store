package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import ro.sd.a2.entity.Client;
import ro.sd.a2.entity.DeliveryAddress;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class DeliveryAddressDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String county;

    private String city;

    private String address;

    private String phone;

    private String id_client;

    public DeliveryAddress toEntity() {

        String id_delivery_address = id;

        if(ObjectUtils.isEmpty(id))
            id_delivery_address = UUID.randomUUID().toString();

        DeliveryAddress delivery_address = DeliveryAddress.builder()
                                                            .id(id_delivery_address)
                                                            .firstName(firstName)
                                                            .lastName(lastName)
                                                            .county(county)
                                                            .city(city)
                                                            .address(address)
                                                            .phone(phone)
                                                            .client(Client.builder().id(id_client).build())
                                                            .build();

        return delivery_address;
    }

    public static DeliveryAddressDTO fromEntity(DeliveryAddress deliveryAddress) {

        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder()
                                                                .id(deliveryAddress.getId())
                                                                .firstName(deliveryAddress.getFirstName())
                                                                .lastName(deliveryAddress.getLastName())
                                                                .county(deliveryAddress.getCounty())
                                                                .city(deliveryAddress.getCity())
                                                                .address(deliveryAddress.getAddress())
                                                                .phone(deliveryAddress.getPhone())
                                                                .id_client(deliveryAddress.getClient().getId())
                                                                .build();

        return deliveryAddressDTO;
    }

}
