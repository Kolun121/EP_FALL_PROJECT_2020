package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends CrudRepository<Employee, Long>, JpaRepository<Employee, Long>{
    List<Employee> findAll();
    List<Employee> findAllByStaffingTableId(Long staffingTableId);
}
