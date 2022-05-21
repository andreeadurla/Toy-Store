package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "credit_card")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    private String id;

    @Column(name = "noCard", nullable = false, unique = true)
    private String noCard;

    @Column(name = "expirationDate", nullable = false)
    private Date expirationDate;

    @Column(name = "CVV", nullable = false)
    private String cvv;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

}
