package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Set<User> findAll();
}
