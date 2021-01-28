/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.Passport;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.PassportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class PassportRestControllerV11 {
    private final EmployeeService employeeService;
    private final PassportService passportService;
    
    public PassportRestControllerV11(
            EmployeeService employeeService,
            PassportService passportService
    ){
        this.employeeService = employeeService;
        this.passportService = passportService;
    }
    
    @GetMapping("{employeeId}/passport") 
    public ResponseEntity<Passport> getPassportByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Passport passport = passportService.findByEmployeeId(employeeId);

        if (passport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(passport, HttpStatus.OK);
    }
    
    @PutMapping("{employeeId}/passport")
    public ResponseEntity<Passport> updatePassportByEmployeeId(@PathVariable("employeeId") Long employeeId, @RequestBody @Valid Passport passport) {
        HttpHeaders headers = new HttpHeaders();
        Employee employee = employeeService.findById(employeeId);
        
        if (passport == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        passport.setId(employee.getPassport().getId());

        employee.setPassport(passport);
        passport.setEmployee(employee);
        passportService.save(passport);

        return new ResponseEntity<>(passport, headers, HttpStatus.OK);
    }

 
}
