package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.Family;

public interface FamilyRepository extends CrudRepository<Family, Long>{
    List<Family> findAllAllByEmployeeId(Long id);
    
}
