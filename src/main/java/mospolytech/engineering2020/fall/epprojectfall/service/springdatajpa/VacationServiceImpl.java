package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mospolytech.engineering2020.fall.epprojectfall.domain.Vacation;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.VacationRepository;
import mospolytech.engineering2020.fall.epprojectfall.service.VacationService;



@Service
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;
    
    public VacationServiceImpl(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    
    @Override
    public Set<Vacation> findAll() {
        Set<Vacation> vacations = new HashSet<>();
        vacationRepository.findAll().forEach(vacations::add);
        return vacations;
    }

    @Override
    public Vacation findById(Long id) {
        Optional<Vacation> vacationOptional = vacationRepository.findById(id);
        
        if (!vacationOptional.isPresent()) {
            throw new RuntimeException("Отпуск не найден по id:" + id );
        }

        return vacationOptional.get();
    }
    
    @Override
    public List<Vacation> findAllByEmployeeId(Long id) {
        List<Vacation> educations = vacationRepository.findAllAllByEmployeeId(id);
 
        return educations;
    }

    @Override
    public Vacation save(Vacation object) {
        return vacationRepository.save(object);
    }

    @Override
    public void delete(Vacation object) {
        vacationRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        vacationRepository.deleteById(id);
    }
}