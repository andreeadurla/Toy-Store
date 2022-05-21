package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.ClientOrder;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class OrderDTO {

    private String id;

    private Date date;

    private float price;

    public static OrderDTO fromEntity(ClientOrder clientOrder) {

        OrderDTO orderDTO = OrderDTO.builder()
                .id(clientOrder.getId())
                .date(clientOrder.getDate())
                .price(clientOrder.getPrice())
                .build();

        return orderDTO;
    }


}
