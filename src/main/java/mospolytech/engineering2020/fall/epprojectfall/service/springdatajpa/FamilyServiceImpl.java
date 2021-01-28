package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mospolytech.engineering2020.fall.epprojectfall.domain.Family;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.FamilyRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.FamilyService;



@Service
public class FamilyServiceImpl<T> implements FamilyService {
    private final FamilyRepository familyRepository;
    
    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    
    @Override
    public Set<Family> findAll() {
        Set<Family> familyMembers = new HashSet<>();
        familyRepository.findAll().forEach(familyMembers::add);
        return familyMembers;
    }

    @Override
    public Family findById(Long id) {
        Optional<Family> familyOptional = familyRepository.findById(id);
        
        if (!familyOptional.isPresent()) {
            return null;
        }

        return familyOptional.get();
    }
    
    @Override
    public List<Family> findAllByEmployeeId(Long id) {
        List<Family> familyMembers = familyRepository.findAllAllByEmployeeId(id);
 
        return familyMembers;
    }

    @Override
    public Family save(Family object) {
        return familyRepository.save(object);
    }

    @Override
    public void delete(Family object) {
        familyRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        familyRepository.deleteById(id);
    }
}