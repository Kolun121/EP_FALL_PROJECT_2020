package mospolytech.engineering2020.fall.epprojectfall.service;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.Vacation;


public interface VacationService extends CrudService<Vacation, Long> {
     List<Vacation> findAllByEmployeeId(Long id);
}