package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.Vacation;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.VacationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class VacationRestControllerV1 {
    private final EmployeeService employeeService;
    private final VacationService vacationService;
    
    public VacationRestControllerV1(
            EmployeeService employeeService,
            VacationService vacationService
    ){
        this.employeeService = employeeService;
        this.vacationService = vacationService;
    }
    
    @GetMapping("{employeeId}/vacations") 
    public ResponseEntity<List<Vacation>> getVacationsByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Vacation> vacations = vacationService.findAllByEmployeeId(employeeId);

        if (vacations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(vacations, HttpStatus.OK);
    }
    
    @PostMapping("{employeeId}/vacations")
    public ResponseEntity<Vacation> saveVacation(
            @PathVariable("employeeId") Long employeeId, 
            @RequestBody Vacation vacation
    ) {
        HttpHeaders headers = new HttpHeaders();

        if (vacation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        vacation.setEmployee(employeeService.findById(employeeId));
        vacationService.save(vacation);
        
        return new ResponseEntity<>(vacation, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("{employeeId}/vacations/{vacationId}")
    public ResponseEntity<Vacation> updateVacationByEmployeeId(
            @PathVariable("employeeId") Long employeeId, 
            @PathVariable("vacationId") Long vacationId,
            @RequestBody @Valid Vacation vacation) {
        HttpHeaders headers = new HttpHeaders();
        
        Vacation vacationCurrent = vacationService.findById(vacationId);
        if (vacationCurrent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        vacation.setEmployee(employeeService.findById(employeeId));
        vacation.setId(vacationId);

        vacationService.save(vacation);

        return new ResponseEntity<>(vacation, headers, HttpStatus.OK);
    }

    @DeleteMapping("{employeeId}/vacations/{vacationId}")
    public ResponseEntity<Vacation> deleteVacation(@PathVariable("vacationId") Long vacationId) {
        Vacation vacation = vacationService.findById(vacationId);

        if (vacation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vacationService.deleteById(vacationId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
