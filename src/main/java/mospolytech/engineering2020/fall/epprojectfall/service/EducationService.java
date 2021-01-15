package mospolytech.engineering2020.fall.epprojectfall.service;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.Education;


public interface EducationService extends CrudService<Education, Long> {
     List<Education> findAllByEmployeeId(Long id);
}