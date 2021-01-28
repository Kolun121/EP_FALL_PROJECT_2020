package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.repository.EmployeeRepository;
import mospolytech.engineering2020.fall.epprojectfall.repository.StaffingTableRepository;

import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Order;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Column;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import mospolytech.engineering2020.fall.epprojectfall.comparator.EmployeeComparators;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Direction;
import org.springframework.util.ObjectUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Comparator<Employee> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final EmployeeRepository employeeRepository;
    private final StaffingTableRepository staffingTableRepository;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, StaffingTableRepository staffingTableRepository) {
        this.employeeRepository = employeeRepository;
        this.staffingTableRepository = staffingTableRepository;
    }
    
    @Override
    public Set<Employee> findAll() {
        Set<Employee> employees = new HashSet<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }
    
    @Override
    public List<Employee> findAllByStaffingTableId(Long staffingTableId) {
        return employeeRepository.findAllByStaffingTableId(staffingTableId);
    }
    
    @Override
    public Employee findById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        
        if (!employeeOptional.isPresent()) {
            return null;
        }

        return employeeOptional.get();
    }

    @Override
    public Employee save(Employee object) {
 
        Employee savedEmployee =  employeeRepository.save(object);
        
        if(object.getStaffingTable() != null){
            StaffingTable staffingTable = staffingTableRepository.findById(object.getStaffingTable().getId()).get();
            staffingTable.setEmployeesNumber(staffingTable.getEmployees().size());
            staffingTableRepository.save(staffingTable);
        }

        return savedEmployee;
    }

    @Override
    public void delete(Employee object) {
        employeeRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
        
    }
    
    @Override
    public void deleteAll(Iterable<Employee> employees) {
        employeeRepository.deleteAll(employees);
    }
    
    @Override
    public void saveAll(Iterable<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public Page<Employee> getEmployees(PagingRequest pagingRequest) {
        List<Employee> employees = employeeRepository.findAll();
       
        return getPage(employees, pagingRequest);
    }
    
     private Page<Employee> getPage(List<Employee> staffingTables, PagingRequest pagingRequest) {
        List<Employee> filtered = staffingTables.stream()
                                           .sorted(sortEmployees(pagingRequest))
                                           .filter(filterEmployees(pagingRequest))
                                           .skip(pagingRequest.getStart())
                                           .limit(pagingRequest.getLength())
                                           .collect(Collectors.toList());

        long count = staffingTables.stream()
                             .filter(filterEmployees(pagingRequest))
                             .count();

        Page<Employee> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());
        
        return page;
    }

    private Predicate<Employee> filterEmployees(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || ObjectUtils.isEmpty(pagingRequest.getSearch()
                                                                                  .getValue())) {
            return employee -> true;
        }
        
     
        String value = pagingRequest.getSearch()
                                    .getValue().toLowerCase();
        
        Predicate<Employee> employeePredicate = employee -> 
        {   
            if(employee.getStaffingTable() == null){
                employee.setStaffingTable(new StaffingTable());
            }
            if(employee.getStaffingTable().getDepartment() == null){
                employee.getStaffingTable().setDepartment(new Department());
                employee.getStaffingTable().getDepartment().setDepartmentName("");
            }
            
            if(employee.getStaffingTable().getPosition() == null){
                employee.getStaffingTable().setPosition(new Position());
                employee.getStaffingTable().getPosition().setPositionName("");
            }
            
            if(employee.getFirstName() == null){
                employee.setFirstName("");
            }
            if(employee.getLastName() == null){
                employee.setLastName("");
            }
            if(employee.getPatronymic() == null){
                employee.setPatronymic("");
            }
            
            if(employee.getEmail() == null){
                employee.setEmail("");
            }
            
            if(employee.getPhoneNumber() == null){
                employee.setPhoneNumber("");
            }
            
            if(employee.getHireDate()== null){
                employee.setHireDate(new Date(0l));
            }
            
            StringBuilder employeeFullName = new StringBuilder();
            employeeFullName.append(employee.getFirstName())
                    .append(" ")
                    .append(employee.getLastName() )
                    .append(" ")
                    .append(employee.getPatronymic());
            
            //При условии, если в строку поиска вводят полное имя (фамилие или/и имя или/и отчество)
            String[] fullNameStrings = value.split(" ");
            Boolean isFullNameTyped = false;

            for(String s: fullNameStrings){
                if(employeeFullName.toString().toLowerCase().contains(s.toLowerCase())){
                    isFullNameTyped = true;
                } else{
                    isFullNameTyped = false;
                    break;
                }
            }
            
            //Приводим дату найма в нужный формат
            LocalDate localDate = employee.getHireDate().toLocalDate().minusDays(1);
            
            int month = localDate.getMonthValue();
            int day = localDate.getDayOfMonth();
            int year = localDate.getYear();
            
            StringBuilder hireDate = new StringBuilder();
            
            if(day < 10){
                hireDate.append("0").append(day).append("/");
            }else{
                hireDate.append(day).append("/");
            }
            
            if(month < 10){
                hireDate.append("0").append(month).append("/");
            }else{
                hireDate.append(month).append("/");
            }
            
            hireDate.append(year);
            
            
            System.out.println(hireDate);
            return employee.getFirstName().toLowerCase().contains(value) || 
                    employee.getLastName().toLowerCase().contains(value) || 
                    employee.getPatronymic().toLowerCase().contains(value) || 
                    isFullNameTyped || 
                    employee.getEmail().toLowerCase().contains(value) ||  
                    employee.getPhoneNumber().contains(value) ||  
                    hireDate.toString().contains(value) ||  
                    employee.getStaffingTable().getDepartment().getDepartmentName().toLowerCase().contains(value) ||  
                    employee.getStaffingTable().getPosition().getPositionName().toLowerCase().contains(value) ||  
                    employee.getId().toString().contains(value);
        };
        
        return employeePredicate;
    }

    private Comparator<Employee> sortEmployees(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                                       .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                                         .get(columnIndex);
            

            Comparator<Employee> comparator = EmployeeComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
          
        }

        return EMPTY_COMPARATOR;
    }

}