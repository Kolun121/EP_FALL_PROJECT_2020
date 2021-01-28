package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.service.DepartmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentRestControllerV1 {
    private final DepartmentService departmentService;
    
    public DepartmentRestControllerV1(
            DepartmentService departmentService
    ){
        this.departmentService = departmentService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Department department = departmentService.findById(id);

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    @GetMapping 
    public ResponseEntity<Set<Department>> getAllDepartments() {
        Set<Department> departments = departmentService.findAll();

        if (departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        HttpHeaders headers = new HttpHeaders();

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        departmentService.save(department);
        
        return new ResponseEntity<>(department, headers, HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Department> updateDepartment(@RequestBody @Valid Department department) {
        HttpHeaders headers = new HttpHeaders();

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        departmentService.save(department);

        return new ResponseEntity<>(department, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") Long id) {
        Department department = departmentService.findById(id);

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        departmentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

 
}
