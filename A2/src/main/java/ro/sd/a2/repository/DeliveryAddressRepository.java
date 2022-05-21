package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.DeliveryAddress;

import java.util.List;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, String> {

    @Modifying
    @Query("update DeliveryAddress d set d.deleted = true where d.id = ?1")
    void deleteById(String id);

    @Modifying
    @Query("select a from DeliveryAddress a where a.deleted = false and a.client.id = ?1")
    List<DeliveryAddress> findByClientId(String clientId);

    DeliveryAddress findByClient_idAndFirstNameAndLastNameAndPhoneAndCountyAndCityAndAddress(
            String client_id, String firstName, String lastName, String phone,
            String county, String city, String address);

}
