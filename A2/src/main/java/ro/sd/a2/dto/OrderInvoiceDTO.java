package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.ClientOrder;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class OrderInvoiceDTO {

    private String id;

    private float price;

    private List<OrderItemDTO> items;

    private String customer;

    private String address;

    public static OrderInvoiceDTO fromEntity(ClientOrder order) {

        OrderInvoiceDTO orderInvoiceDTO = OrderInvoiceDTO.builder()
                .id(order.getId())
                .price(order.getPrice())
                .items(order.getItems().stream()
                        .map(o -> OrderItemDTO.fromEntity(o))
                        .collect(Collectors.toList()))
                .customer(order.getCustomerName())
                .address(order.getCustomerAddress())
                .build();

        return orderInvoiceDTO;
    }
}
