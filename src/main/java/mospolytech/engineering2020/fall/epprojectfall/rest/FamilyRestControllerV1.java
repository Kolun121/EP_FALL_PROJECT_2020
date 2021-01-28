package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.Family;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.FamilyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class FamilyRestControllerV1 {
    private final EmployeeService employeeService;
    private final FamilyService familyService;
    
    public FamilyRestControllerV1(
            EmployeeService employeeService,
            FamilyService familyService
    ){
        this.employeeService = employeeService;
        this.familyService = familyService;
    }
    
    @GetMapping("{employeeId}/family") 
    public ResponseEntity<List<Family>> getFamilysByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Family> familys = familyService.findAllByEmployeeId(employeeId);

        if (familys.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(familys, HttpStatus.OK);
    }
    
    @PostMapping("{employeeId}/family")
    public ResponseEntity<Family> saveFamily(
            @PathVariable("employeeId") Long employeeId, 
            @RequestBody Family family
    ) {
        HttpHeaders headers = new HttpHeaders();

        if (family == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        family.setEmployee(employeeService.findById(employeeId));
        familyService.save(family);
        
        return new ResponseEntity<>(family, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("{employeeId}/family/{familyId}")
    public ResponseEntity<Family> updateFamilyByEmployeeId(
            @PathVariable("employeeId") Long employeeId, 
            @PathVariable("familyId") Long familyId,
            @RequestBody @Valid Family family) {
        HttpHeaders headers = new HttpHeaders();
        
        Family familyCurrent = familyService.findById(familyId);
        if (familyCurrent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        family.setEmployee(employeeService.findById(employeeId));
        family.setId(familyId);

        familyService.save(family);

        return new ResponseEntity<>(family, headers, HttpStatus.OK);
    }

    @DeleteMapping("{employeeId}/family/{familyId}")
    public ResponseEntity<Family> deleteVacation(@PathVariable("familyId") Long familyId) {
        Family family = familyService.findById(familyId);

        if (family == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        familyService.deleteById(familyId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
