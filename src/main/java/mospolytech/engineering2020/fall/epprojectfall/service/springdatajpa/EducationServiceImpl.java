package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mospolytech.engineering2020.fall.epprojectfall.domain.Education;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.EducationRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.EducationService;



@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    
    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    
    @Override
    public Set<Education> findAll() {
        Set<Education> educations = new HashSet<>();
        educationRepository.findAll().forEach(educations::add);
        return educations;
    }

    @Override
    public Education findById(Long id) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        
        if (!educationOptional.isPresent()) {
            return null;
        }

        return educationOptional.get();
    }
    
    @Override
    public List<Education> findAllByEmployeeId(Long id) {
        List<Education> educations = educationRepository.findAllAllByEmployeeId(id);
 
        return educations;
    }

    @Override
    public Education save(Education object) {
        return educationRepository.save(object);
    }

    @Override
    public void delete(Education object) {
        educationRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}