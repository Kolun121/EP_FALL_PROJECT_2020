package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.StaffingTableRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.StaffingTableService;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Order;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Column;
import mospolytech.engineering2020.fall.epprojectfall.comparator.StaffingTableComparators;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import org.springframework.util.ObjectUtils;


@Service
public class StaffingTableServiceImpl implements StaffingTableService {
    private static final Comparator<StaffingTable> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final StaffingTableRepository staffingTableRepository;
    
    public StaffingTableServiceImpl(StaffingTableRepository staffingTableRepository) {
        this.staffingTableRepository = staffingTableRepository;
    }

    
    @Override
    public Set<StaffingTable> findAll() {
        Set<StaffingTable> staffingTables = new HashSet<>();
        staffingTableRepository.findAll().forEach(staffingTables::add);
        return staffingTables;
    }

    @Override
    public StaffingTable findById(Long id) {
        Optional<StaffingTable> staffingTableOptional = staffingTableRepository.findById(id);
        
        if (!staffingTableOptional.isPresent()) {
            throw new RuntimeException("Запись не найдена по id:" + id );
        }

        return staffingTableOptional.get();
    }
    
    @Override
    public StaffingTable save(StaffingTable object) {
        object.setEmployeesNumber(object.getEmployees().size());
        return staffingTableRepository.save(object);
    }

    @Override
    public void delete(StaffingTable object) {
        staffingTableRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        staffingTableRepository.deleteById(id);
    }
    
    @Override
    public void deleteAll(Iterable<StaffingTable> staffingTables) {
        staffingTableRepository.deleteAll(staffingTables);
    }
    
    @Override
    public Page<StaffingTable> getStaffingTables(PagingRequest pagingRequest) {
        List<StaffingTable> staffingTables = staffingTableRepository.findAll();
       
        return getPage(staffingTables, pagingRequest);
    }
    
     private Page<StaffingTable> getPage(List<StaffingTable> staffingTables, PagingRequest pagingRequest) {
        List<StaffingTable> filtered = staffingTables.stream()
                                           .sorted(sortStaffingTables(pagingRequest))
                                           .filter(filterStaffingTables(pagingRequest))
                                           .skip(pagingRequest.getStart())
                                           .limit(pagingRequest.getLength())
                                           .collect(Collectors.toList());

        long count = staffingTables.stream()
                             .filter(filterStaffingTables(pagingRequest))
                             .count();

        Page<StaffingTable> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<StaffingTable> filterStaffingTables(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || ObjectUtils.isEmpty(pagingRequest.getSearch()
                                                                                  .getValue())) {
            return staffingTable -> true;
        }
        
     
        String value = pagingRequest.getSearch()
                                    .getValue();
        
        Predicate<StaffingTable> staffingTablePredicate = staffingTable -> 
        {
            if(staffingTable.getDepartment() == null){
                staffingTable.setDepartment(new Department());
                staffingTable.getDepartment().setDepartmentName("");
            }
            
            if(staffingTable.getPosition() == null){
                staffingTable.setPosition(new Position());
                staffingTable.getPosition().setPositionName("");
            }
            
            if(staffingTable.getSalary() == null){
                staffingTable.setSalary(-1l);
            }
            
            return staffingTable.getDepartment().getDepartmentName().contains(value) || 
                    staffingTable.getPosition().getPositionName().contains(value) || 
                    staffingTable.getId().toString().contains(value) || 
                    staffingTable.getSalary().toString().contains(value);
        };
        
        return staffingTablePredicate;
    }

    private Comparator<StaffingTable> sortStaffingTables(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                                       .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                                         .get(columnIndex);

            Comparator<StaffingTable> comparator = StaffingTableComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
          
        }

        return EMPTY_COMPARATOR;
    }
}