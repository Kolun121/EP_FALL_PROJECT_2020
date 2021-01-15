package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.Passport;


public interface PassportRepository extends CrudRepository<Passport, Long>{
    Optional<Passport> findByEmployeeId(Long employeeId);
}
