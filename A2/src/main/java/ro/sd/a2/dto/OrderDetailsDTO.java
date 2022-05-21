package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.ClientOrder;
import ro.sd.a2.entity.DeliveryMethod;
import ro.sd.a2.entity.OrderStatus;
import ro.sd.a2.entity.PaymentMethod;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class OrderDetailsDTO {

    private String id;

    private String clientId;

    private Date date;

    private float price;

    private List<OrderItemDTO> items;

    private DeliveryAddressDTO deliveryAddress;

    private DeliveryMethod deliveryMethod;

    private PaymentMethod paymentMethod;

    private OrderStatus status;

    private boolean paid;

    public static OrderDetailsDTO fromEntity(ClientOrder order) {

        OrderDetailsDTO orderDetailsDTO = OrderDetailsDTO.builder()
                .id(order.getId())
                .clientId(order.getClient().getId())
                .date(order.getDate())
                .price(order.getPrice())
                .items(order.getItems().stream()
                                            .map(o -> OrderItemDTO.fromEntity(o))
                                            .collect(Collectors.toList()))
                .deliveryAddress(DeliveryAddressDTO.fromEntity(order.getDelivery_address()))
                .deliveryMethod(order.getDelivery_method())
                .paymentMethod(order.getPayment_method())
                .status(order.getStatus())
                .paid(order.isPaid())
                .build();

        return orderDetailsDTO;
    }

}
