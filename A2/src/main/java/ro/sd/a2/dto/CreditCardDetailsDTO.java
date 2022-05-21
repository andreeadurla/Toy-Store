package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.CreditCard;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CreditCardDetailsDTO {

    private String id;

    private String noCard;

    private Date expirationDate;

    private String name;

    public static CreditCardDetailsDTO fromEntity(CreditCard creditCard) {

        CreditCardDetailsDTO creditCardDetailsDTO = CreditCardDetailsDTO.builder()
                                                            .id(creditCard.getId())
                                                            .noCard(creditCard.getNoCard())
                                                            .expirationDate(creditCard.getExpirationDate())
                                                            .name(creditCard.getName())
                                                            .build();

        return creditCardDetailsDTO;
    }

}
