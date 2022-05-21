package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Client findByEmailAndPassword(String email, String password);

    Client findByEmail(String email);

}
