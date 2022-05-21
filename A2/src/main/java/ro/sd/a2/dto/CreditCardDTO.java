package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.Client;
import ro.sd.a2.entity.CreditCard;
import ro.sd.a2.utils.ApplicationUtils;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreditCardDTO {

    private String noCard;

    private String expirationDate;

    private String cvv;

    private String name;

    private String idClient;

    public CreditCard toEntity() {

        String id = UUID.randomUUID().toString();
        Date date = ApplicationUtils.convertFromStringYMToDate(expirationDate);

        CreditCard creditCard = CreditCard.builder()
                                        .id(id)
                                        .noCard(noCard)
                                        .expirationDate(date)
                                        .cvv(cvv)
                                        .name(name)
                                        .client(Client.builder().id(idClient).build())
                                        .build();

        return creditCard;
    }

}
