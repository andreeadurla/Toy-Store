package ro.utcn.amqp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderItemDTO {

    private String name;

    private int quantity;

    private float unit_price;

    private float price;

    private String imageUrl;
}
