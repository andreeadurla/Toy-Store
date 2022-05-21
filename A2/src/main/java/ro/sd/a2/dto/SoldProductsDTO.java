package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class SoldProductsDTO {

    private Date startDate;

    private Date endDate;

    private Map<String, Integer> products;
}
