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
public class NotificationUnsoldProductsDTO {

    private String subject;

    private Date date;

    private String adminEmail;

    private List<UnsoldProductDTO> unsoldProducts;
}