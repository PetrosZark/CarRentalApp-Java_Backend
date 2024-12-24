package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);
    Optional<User> findByVat(String vat);
    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);
}
