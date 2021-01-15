package mospolytech.engineering2020.fall.epprojectfall.service;

import mospolytech.engineering2020.fall.epprojectfall.domain.Passport;


public interface PassportService extends CrudService<Passport, Long> {
     Passport findByEmployeeId(Long id);
}