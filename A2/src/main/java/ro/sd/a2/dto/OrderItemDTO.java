package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.OrderItem;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDTO {

    private String name;

    private int quantity;

    private float unit_price;

    private float price;

    private String imageUrl;

    public static OrderItemDTO fromEntity(OrderItem orderItem) {

        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .name(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .unit_price(orderItem.getUnit_price())
                .price(orderItem.getTotal_price())
                .imageUrl(orderItem.getProductImageURL())
                .build();

        return orderItemDTO;
    }

}
