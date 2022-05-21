package ro.utcn.amqp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UnsoldProductDTO {

    private String name;

    private float price;

    private int quantity;

    private String imageURL;

    private Date lastSale;
}