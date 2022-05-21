package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NotificationUnsoldProductsDTO {

    private String subject;

    private Date date;

    private String adminEmail;

    private List<UnsoldProductDTO> unsoldProducts;

}
