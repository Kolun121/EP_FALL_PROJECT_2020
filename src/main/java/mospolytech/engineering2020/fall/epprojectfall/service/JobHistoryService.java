package mospolytech.engineering2020.fall.epprojectfall.service;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.JobHistory;


public interface JobHistoryService extends CrudService<JobHistory, Long> {
     List<JobHistory> findAllByEmployeeId(Long id);
}