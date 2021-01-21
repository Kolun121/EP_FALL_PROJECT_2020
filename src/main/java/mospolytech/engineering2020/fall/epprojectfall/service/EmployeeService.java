package mospolytech.engineering2020.fall.epprojectfall.service;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Paged;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface EmployeeService extends CrudService<Employee, Long> {
//    Page<Employee> findAllPageableSpec(Specification<Employee> filter, Pageable pageable);
    Paged<Employee> getPage(int pageNumber, int size);
    void saveAll(List<Employee> employees);
    List<Employee> findAllByStaffingTableId(Long staffingTableId);
}