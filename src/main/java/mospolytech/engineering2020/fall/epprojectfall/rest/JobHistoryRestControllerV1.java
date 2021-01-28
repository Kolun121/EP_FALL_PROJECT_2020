package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.JobHistory;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.JobHistoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class JobHistoryRestControllerV1 {
    private final EmployeeService employeeService;
    private final JobHistoryService jobHistoryService;
    
    public JobHistoryRestControllerV1(
            EmployeeService employeeService,
            JobHistoryService jobHistoryService
    ){
        this.employeeService = employeeService;
        this.jobHistoryService = jobHistoryService;
    }
    
    @GetMapping("{employeeId}/jobHistory") 
    public ResponseEntity<List<JobHistory>> getJobHistoryByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<JobHistory> jobHistorys = jobHistoryService.findAllByEmployeeId(employeeId);

        if (jobHistorys.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(jobHistorys, HttpStatus.OK);
    }
    
    @PostMapping("{employeeId}/jobHistory")
    public ResponseEntity<JobHistory> saveJobHistory(
            @PathVariable("employeeId") Long employeeId, 
            @RequestBody JobHistory jobHistory
    ) {
        HttpHeaders headers = new HttpHeaders();

        if (jobHistory == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        jobHistory.setEmployee(employeeService.findById(employeeId));
        jobHistoryService.save(jobHistory);
        
        return new ResponseEntity<>(jobHistory, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("{employeeId}/jobHistory/{jobHistoryId}")
    public ResponseEntity<JobHistory> updateJobHistoryByEmployeeId(
            @PathVariable("employeeId") Long employeeId, 
            @PathVariable("jobHistoryId") Long jobHistoryId,
            @RequestBody @Valid JobHistory jobHistory) {
        HttpHeaders headers = new HttpHeaders();
        
        JobHistory jobHistoryCurrent = jobHistoryService.findById(jobHistoryId);
        if (jobHistoryCurrent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        jobHistory.setEmployee(employeeService.findById(employeeId));
        jobHistory.setId(jobHistoryId);

        jobHistoryService.save(jobHistory);

        return new ResponseEntity<>(jobHistory, headers, HttpStatus.OK);
    }

    @DeleteMapping("{employeeId}/jobHistory/{jobHistoryId}")
    public ResponseEntity<JobHistory> deleteJobHistory(@PathVariable("jobHistoryId") Long jobHistoryId) {
        JobHistory jobHistory = jobHistoryService.findById(jobHistoryId);

        if (jobHistory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        jobHistoryService.deleteById(jobHistoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
