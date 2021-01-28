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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeRestControllerV1 {
    private final EmployeeService employeeService;
    
    public EmployeeRestControllerV1(
            EmployeeService employeeService
    ){
        this.employeeService = employeeService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeService.findById(id);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        HttpHeaders headers = new HttpHeaders();

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Passport newPassport = new Passport();
        employee.setPassport(newPassport);
        employeeService.save(employee);
        
        return new ResponseEntity<>(employee, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody @Valid Employee employee) {
        HttpHeaders headers = new HttpHeaders();

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        employee.getPassport().setEmployee(employee);
        employeeService.save(employee);

        return new ResponseEntity<>(employee, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id) {
        Employee customer = employeeService.findById(id);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @GetMapping
    public ResponseEntity<Set<Employee>> getAllEmployees() {
        
        Set<Employee> employees = employeeService.findAll();

        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
