package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.domain.Passport;
import mospolytech.engineering2020.fall.epprojectfall.repository.PassportRepository;

import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Paged;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Paging;
import mospolytech.engineering2020.fall.epprojectfall.service.PassportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@Service
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    
    public PassportServiceImpl(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    
    @Override
    public Set<Passport> findAll() {
        Set<Passport> passports = new HashSet<>();
        passportRepository.findAll().forEach(passports::add);
        return passports;
    }

    @Override
    public Passport findById(Long id) {
        Optional<Passport> passportOptional = passportRepository.findById(id);
        
        if (!passportOptional.isPresent()) {
            throw new RuntimeException("Паспорт не найден по id:" + id );
        }

        return passportOptional.get();
    }
    
    @Override
    public Passport findByEmployeeId(Long id) {
        Optional<Passport> passportOptional = passportRepository.findByEmployeeId(id);
        
        if (!passportOptional.isPresent()) {
            throw new RuntimeException("Паспорт не найден по id сотрудника:" + id );
        }

        return passportOptional.get();
    }

    @Override
    public Passport save(Passport object) {
        return passportRepository.save(object);
    }

    @Override
    public void delete(Passport object) {
        passportRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        passportRepository.deleteById(id);
    }


}