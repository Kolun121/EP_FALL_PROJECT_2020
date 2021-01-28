package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.PositionService;
import mospolytech.engineering2020.fall.epprojectfall.service.StaffingTableService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staffingTables")
public class StaffingTableRestControllerV1 {
    private final StaffingTableService staffingTableService;
    private final EmployeeService employeeService;
//    private final PositionService positionService;
    
    public StaffingTableRestControllerV1(
            StaffingTableService staffingTableService,
            EmployeeService employeeService
    ){
        this.staffingTableService = staffingTableService;
        this.employeeService = employeeService;
    }
    
    @GetMapping("{id}") 
    public ResponseEntity<StaffingTable> getStaffingTablesById(@PathVariable("staffingTableId") Long staffingTableId) {
        if (staffingTableId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        StaffingTable staffingTable = staffingTableService.findById(staffingTableId);

        if (staffingTable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(staffingTable, HttpStatus.OK);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<StaffingTable> deleteStaffingTable(@PathVariable("id") Long id) {
        StaffingTable staffingTable = staffingTableService.findById(id);

        if (staffingTable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        staffingTableService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @GetMapping
    public ResponseEntity<Set<StaffingTable>> getAllStaffingTables() {
        
        Set<StaffingTable> staffingTables = staffingTableService.findAll();

        if (staffingTables.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(staffingTables, HttpStatus.OK);
    }
}
