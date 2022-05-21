package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class NotificationSoldProductsDTO {

    private String subject;

    private Date date;

    private String adminEmail;

    private Map<String, Integer> soldProducts;

}
