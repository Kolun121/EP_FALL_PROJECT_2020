package mospolytech.engineering2020.fall.epprojectfall.service;

import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;


public interface DepartmentService extends CrudService<Department, Long> {
    Page<Department> getDepartments(PagingRequest pagingRequest);
    void saveAll(Iterable<Department> departments);
    void deleteAll(Iterable<Department> departments);
}