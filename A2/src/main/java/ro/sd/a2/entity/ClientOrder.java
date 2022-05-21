package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientOrder {

    @Id
    private String id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "observation")
    private String observation;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "shipping_cost")
    private float shipping_cost;

    @Column(name = "delivery_method", nullable = false)
    private DeliveryMethod delivery_method;

    @Column(name = "payment_method", nullable = false)
    private PaymentMethod payment_method;

    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress delivery_address;

    @OneToMany(mappedBy = "client_order", fetch = FetchType.LAZY)
    private List<OrderItem> items;

    public String getCustomerName() {
        return client.getFirstName() + " " + client.getLastName();
    }

    public String getCustomerAddress() {
        return delivery_address.getCounty() + ", " + delivery_address.getCity() + ", " + delivery_address.getAddress();
    }

}
