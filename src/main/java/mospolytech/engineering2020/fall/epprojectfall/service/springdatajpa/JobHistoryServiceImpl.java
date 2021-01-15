package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mospolytech.engineering2020.fall.epprojectfall.domain.JobHistory;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.JobHistoryRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.JobHistoryService;



@Service
public class JobHistoryServiceImpl implements JobHistoryService {
    private final JobHistoryRepository jobHistoryRepository;
    
    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    
    @Override
    public Set<JobHistory> findAll() {
        Set<JobHistory> jobHistorys = new HashSet<>();
        jobHistoryRepository.findAll().forEach(jobHistorys::add);
        return jobHistorys;
    }

    @Override
    public JobHistory findById(Long id) {
        Optional<JobHistory> jobHistoryOptional = jobHistoryRepository.findById(id);
        
        if (!jobHistoryOptional.isPresent()) {
            throw new RuntimeException("Предыдущая работа не найдена по id:" + id );
        }

        return jobHistoryOptional.get();
    }
    
    @Override
    public List<JobHistory> findAllByEmployeeId(Long id) {
        List<JobHistory> jobHistorys = jobHistoryRepository.findAllAllByEmployeeId(id);
 
        return jobHistorys;
    }

    @Override
    public JobHistory save(JobHistory object) {
        return jobHistoryRepository.save(object);
    }

    @Override
    public void delete(JobHistory object) {
        jobHistoryRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        jobHistoryRepository.deleteById(id);
    }
}