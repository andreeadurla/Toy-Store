package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements User, UserDetails {

    @Id
    private String id;

    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<DeliveryAddress> delivery_addresses;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<ShoppingCartItem> shopping_cart_items;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<ClientOrder> orders;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<CreditCard> creditCards;

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("USER"));

        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(AccountStatus.ENABLE);
    }
}
