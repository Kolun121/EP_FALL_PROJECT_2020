package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.sql.Date;
import java.util.Arrays;
import mospolytech.engineering2020.fall.epprojectfall.service.JobHistoryService;
import mospolytech.engineering2020.fall.epprojectfall.domain.JobHistory;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller("adminJobHistoryController")
@RequestMapping("/admin/employees")
public class JobHistoryController {
    private final EmployeeService employeeService;
    private final JobHistoryService jobHistoryService;
    
    public JobHistoryController(JobHistoryService jobHistoryService, EmployeeService employeeService) {
        this.jobHistoryService = jobHistoryService;
        this.employeeService = employeeService;
    }
    
    @GetMapping("{id}/jobhistory") 
    public String getJobHistoryByEmployeeId(@PathVariable String id, Model model) {
        model.addAttribute("employeeId", id);
        model.addAttribute("jobHistoryItems", jobHistoryService.findAll());
        
        return "admin/job_history/updateJobHistory";
    }
    
    @PostMapping("{id}/jobhistory/new")
    public ModelAndView createJobHistoryItem(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/fragments/employee/employeeJobHistoryItem :: employeeJobHistoryItem ");
        JobHistory jobHistoryItem = new JobHistory();
        jobHistoryItem.setEmployee(employeeService.findById(id));
        mav.addObject("jobHistoryItems", Arrays.asList(jobHistoryService.save(jobHistoryItem)));
        return mav;
    }
    
    @PostMapping("{employeeId}/jobhistory/{jobHistoryId}")
    public @ResponseBody void updateJobHistoryById(
            @RequestParam(value = "jobTitle", required = false) String jobTitle, 
            @RequestParam(value = "startDate", required = false) String startDate, 
            @RequestParam(value = "endDate", required = false) String endDate, 
            @PathVariable Long employeeId, 
            @PathVariable Long jobHistoryId)
    {
        JobHistory jobHistoryItem = new JobHistory();
        
        jobHistoryItem.setId(jobHistoryId);
        jobHistoryItem.setJobTitle(jobTitle);

        if(!startDate.isEmpty()){
            jobHistoryItem.setStartDate(Date.valueOf(startDate));
        }

        if(!endDate.isEmpty()){
            jobHistoryItem.setEndDate(Date.valueOf(endDate));
        }

        jobHistoryItem.setEmployee(employeeService.findById(employeeId));
        jobHistoryService.save(jobHistoryItem);
    }
    
    @DeleteMapping("{employeeId}/jobhistory/{jobHistoryId}")
    public @ResponseBody void deleteJobHistoryById(@PathVariable Long jobHistoryId){
        jobHistoryService.deleteById(jobHistoryId);
    }
}
