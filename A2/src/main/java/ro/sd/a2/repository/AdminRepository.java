package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    public Admin findByEmailAndPassword(String email, String password);

    public Admin findByEmail(String email);
}
