package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;

public interface DepartmentRepository extends CrudRepository<Department, Long>{
    List<Department> findAll();
}
