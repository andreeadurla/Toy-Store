package ro.utcn.amqp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDetailsDTO {

    private String id;

    private UserDTO user;

    private Date date;

    private float price;

    private List<OrderItemDTO> items;

    private DeliveryAddressDTO deliveryAddress;

    private DeliveryMethod deliveryMethod;

    private PaymentMethod paymentMethod;

    private boolean paid;
}
