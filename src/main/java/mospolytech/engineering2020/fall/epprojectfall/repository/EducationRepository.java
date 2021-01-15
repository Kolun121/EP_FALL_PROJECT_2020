package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.Education;

public interface EducationRepository extends CrudRepository<Education, Long>{
    List<Education> findAllAllByEmployeeId(Long id);
    
}
