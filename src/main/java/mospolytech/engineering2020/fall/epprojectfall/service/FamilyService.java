package mospolytech.engineering2020.fall.epprojectfall.service;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.Family;


public interface FamilyService extends CrudService<Family, Long> {
     List<Family> findAllByEmployeeId(Long id);
}