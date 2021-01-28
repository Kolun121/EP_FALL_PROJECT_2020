package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Order;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Column;
import mospolytech.engineering2020.fall.epprojectfall.comparator.DepartmentComparators;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.DepartmentRepository;
import mospolytech.engineering2020.fall.epprojectfall.repository.StaffingTableRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.DepartmentService;
import org.springframework.util.ObjectUtils;



@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Comparator<Department> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final DepartmentRepository departmentRepository;
    private final StaffingTableRepository staffingTableRepository;
    
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, StaffingTableRepository staffingTableRepository) {
        this.departmentRepository = departmentRepository;
        this.staffingTableRepository = staffingTableRepository;
    }

    
    @Override
    public Set<Department> findAll() {
        Set<Department> positions = new HashSet<>();
        departmentRepository.findAll().forEach(positions::add);
        return positions;
    }

    @Override
    public Department findById(Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        
        if (!departmentOptional.isPresent()) {
            return null;
        }

        return departmentOptional.get();
    }
    
    @Override
    public void saveAll(Iterable<Department> departments) {
        departmentRepository.saveAll(departments);
    }
    
    @Override
    public Department save(Department object) {
        return departmentRepository.save(object);
    }

    @Override
    public void delete(Department object) {
        Set<StaffingTable> staffingTables = new HashSet<>();
        
        Department fetchedDepartment = departmentRepository.findById(object.getId()).get();
        
        staffingTables.addAll(fetchedDepartment.getStaffingTable());

        
        for(StaffingTable staffingTable: staffingTables){
            staffingTable.setDepartment(null);
        }
        
        staffingTableRepository.saveAll(staffingTables);
        
        departmentRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        Set<StaffingTable> staffingTables = new HashSet<>();
        
        Department fetchedDepartment = departmentRepository.findById(id).get();
        
        staffingTables.addAll(fetchedDepartment.getStaffingTable());

        
        for(StaffingTable staffingTable: staffingTables){
            staffingTable.setDepartment(null);
        }
        
        staffingTableRepository.saveAll(staffingTables);
        departmentRepository.deleteById(id);
    }
    @Override
    public void deleteAll(Iterable<Department> departments) {
        Set<StaffingTable> staffingTables = new HashSet<>();
        Set<Long> departmentsIds = new HashSet<>();
        
        for(Department department: departments){
            departmentsIds.add(department.getId());
        }
        
        Iterable<Department> fetchedDepartments = departmentRepository.findAllById(departmentsIds);
        
        for(Department department: fetchedDepartments){
            staffingTables.addAll(department.getStaffingTable());
        }
        
        for(StaffingTable staffingTable: staffingTables){
            staffingTable.setDepartment(null);
        }
        
        staffingTableRepository.saveAll(staffingTables);
        departmentRepository.deleteAll(departments);
    }
    

    @Override
    public Page<Department> getDepartments(PagingRequest pagingRequest) {
        List<Department> departments = departmentRepository.findAll();
        return getPage(departments, pagingRequest);
    }
    
     private Page<Department> getPage(List<Department> departments, PagingRequest pagingRequest) {
        List<Department> filtered = departments.stream()
                                           .sorted(sortDepartments(pagingRequest))
                                           .filter(filterDepartments(pagingRequest))
                                           .skip(pagingRequest.getStart())
                                           .limit(pagingRequest.getLength())
                                           .collect(Collectors.toList());

        long count = departments.stream()
                             .filter(filterDepartments(pagingRequest))
                             .count();

        Page<Department> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<Department> filterDepartments(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || ObjectUtils.isEmpty(pagingRequest.getSearch()
                                                                                  .getValue())) {
            return department -> true;
        }
        
     
        String value = pagingRequest.getSearch()
                                    .getValue().toLowerCase();
        

        Predicate<Department> departmentPredicate = department -> 
        {
            if(department.getDepartmentName() == null){
                department.setDepartmentName("");
            }
            return department.getId().toString().contains(value) || 
                    department.getDepartmentName().toLowerCase().contains(value);
        };
        
        return departmentPredicate;
    }

    private Comparator<Department> sortDepartments(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                                       .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                                         .get(columnIndex);

            Comparator<Department> comparator = DepartmentComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
          
        }

        return EMPTY_COMPARATOR;
    }

}