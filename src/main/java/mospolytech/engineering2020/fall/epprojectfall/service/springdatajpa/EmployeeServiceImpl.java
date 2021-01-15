package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.repository.EmployeeRepository;

import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Paged;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Paging;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    
    public Paged<Employee> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Employee> postPage = employeeRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }
    
    @Override
    public Set<Employee> findAll() {
        Set<Employee> employees = new HashSet<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        
        if (!employeeOptional.isPresent()) {
            throw new RuntimeException("Сотрудник не найден по id:" + id );
        }

        return employeeOptional.get();
    }

    @Override
    public Employee save(Employee object) {
        return employeeRepository.save(object);
    }

    @Override
    public void delete(Employee object) {
        employeeRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }


}