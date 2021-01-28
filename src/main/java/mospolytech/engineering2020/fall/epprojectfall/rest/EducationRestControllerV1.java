package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.Education;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.EducationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class EducationRestControllerV1 {
    private final EmployeeService employeeService;
    private final EducationService educationService;
    
    public EducationRestControllerV1(
            EmployeeService employeeService,
            EducationService educationService
    ){
        this.employeeService = employeeService;
        this.educationService = educationService;
    }
    
    @GetMapping("{employeeId}/educations") 
    public ResponseEntity<List<Education>> getEducationsByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Education> educations = educationService.findAllByEmployeeId(employeeId);

        if (educations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(educations, HttpStatus.OK);
    }
    
    @PostMapping("{employeeId}/educations")
    public ResponseEntity<Education> saveEducation(
            @PathVariable("employeeId") Long employeeId, 
            @RequestBody Education education
    ) {
        HttpHeaders headers = new HttpHeaders();

        if (education == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        education.setEmployee(employeeService.findById(employeeId));
        educationService.save(education);
        
        return new ResponseEntity<>(education, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("{employeeId}/educations/{educationId}")
    public ResponseEntity<Education> updateEducationsByEmployeeId(
            @PathVariable("employeeId") Long employeeId, 
            @PathVariable("educationId") Long educationId,
            @RequestBody @Valid Education education) {
        HttpHeaders headers = new HttpHeaders();
        
        Education educationCurrent = educationService.findById(educationId);
        if (educationCurrent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        education.setEmployee(employeeService.findById(employeeId));
        education.setId(educationId);

        educationService.save(education);

        return new ResponseEntity<>(education, headers, HttpStatus.OK);
    }

    @DeleteMapping("{employeeId}/educations/{educationId}")
    public ResponseEntity<Education> deleteEducation(@PathVariable("educationId") Long educationId) {
        Education education = educationService.findById(educationId);

        if (education == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        educationService.deleteById(educationId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
