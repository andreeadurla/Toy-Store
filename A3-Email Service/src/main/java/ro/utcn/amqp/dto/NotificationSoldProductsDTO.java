package ro.utcn.amqp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NotificationSoldProductsDTO {

    private String subject;

    private Date date;

    private String adminEmail;

    private Map<String, Integer> soldProducts;

}
