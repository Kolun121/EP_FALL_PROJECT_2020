package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Order;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Column;
import mospolytech.engineering2020.fall.epprojectfall.comparator.DepartmentComparators;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.DepartmentRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.DepartmentService;
import org.springframework.util.ObjectUtils;



@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Comparator<Department> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final DepartmentRepository departmentRepository;
    
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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
            throw new RuntimeException("Отдел не найден по id:" + id );
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
        departmentRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        
        departmentRepository.deleteById(id);
    }
    @Override
    public void deleteAll(Iterable<Department> departments) {
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
                                    .getValue();
        
//        if(department){
//        
//        }
        Predicate<Department> departmentPredicate = department -> 
        {
            if(department.getDepartmentName() == null){
                department.setDepartmentName("");
            }
            return department.getId().toString().contains(value) || 
                    department.getDepartmentName().contains(value);
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