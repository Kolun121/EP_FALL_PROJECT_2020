package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;

public interface StaffingTableRepository extends CrudRepository<StaffingTable, Long>{
    List<StaffingTable> findAll();
}
