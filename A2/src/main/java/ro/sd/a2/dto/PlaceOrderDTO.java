package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class PlaceOrderDTO {

    private String id_client;

    private String id_delivery_address;

    private String observations;

    private PaymentMethod paymentMethod;

    private DeliveryMethod deliveryMethod;

    private float price;

    private boolean paid;

    public ClientOrder toEntity() {

        String id = UUID.randomUUID().toString();

        ClientOrder order = ClientOrder.builder()
                .id(id)
                .date(new Date())
                .observation(observations)
                .price(price)
                .delivery_method(deliveryMethod)
                .payment_method(paymentMethod)
                .client(Client.builder().id(id_client).build())
                .delivery_address(DeliveryAddress.builder().id(id_delivery_address).build())
                .status(OrderStatus.IN_PROCESSING)
                .paid(paid)
                .build();

        return order;
    }

    public boolean paymentWithCard() {
        return paymentMethod.equals(PaymentMethod.CARD);
    }

}
