package mospolytech.engineering2020.fall.epprojectfall.service;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;

public interface EmployeeService extends CrudService<Employee, Long> {
    Page<Employee> getEmployees(PagingRequest pagingRequest);
    void deleteAll(Iterable<Employee> employees);
    void saveAll(Iterable<Employee> employees);
    List<Employee> findAllByStaffingTableId(Long staffingTableId);
}