package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.sql.Date;
import java.util.Arrays;
import mospolytech.engineering2020.fall.epprojectfall.service.EducationService;
import mospolytech.engineering2020.fall.epprojectfall.domain.Education;
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


@Controller("adminEducationController")
@RequestMapping("/admin/employees")
public class EducationController {
    private final EmployeeService employeeService;
    private final EducationService educationService;
    
    public EducationController(EducationService educationService, EmployeeService employeeService) {
        this.educationService = educationService;
        this.employeeService = employeeService;
    }
    
    @GetMapping("{id}/education") 
    public String getPassportByEmployeeId(@PathVariable String id, Model model) {
//        Passport passport = passportService.findByEmployeeId(Long.parseLong(id));

        model.addAttribute("employeeId", id);
        model.addAttribute("educations", educationService.findAll());
        
        return "admin/education/updateEducation";
    }
    
    @PostMapping("{id}/education/new")
    public ModelAndView createEducationalStageItem(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/fragments/employee/employeeEducationItem :: employeeEducationItem ");
        Education educationItem = new Education();
        educationItem.setEmployee(employeeService.findById(id));
        mav.addObject("educationItems", Arrays.asList(educationService.save(educationItem)));
        return mav;
    }
    
    @PostMapping("{employeeId}/education/{educationId}")
    public @ResponseBody void updateEducationById(
            @RequestParam(value = "specialty", required = false) String specialty, 
            @RequestParam(value = "qualification", required = false) String qualification, 
            @RequestParam(value = "graduationDate", required = false) String graduationDate, 
            @PathVariable Long employeeId, 
            @PathVariable Long educationId)
    {
        Education educationItem = new Education();
        
        educationItem.setId(educationId);
        educationItem.setSpecialty(specialty);
        educationItem.setQualification(qualification);
        educationItem.setGraduationDate(Date.valueOf(graduationDate));

        educationItem.setEmployee(employeeService.findById(employeeId));
        educationService.save(educationItem);
    }
    
    @DeleteMapping("{employeeId}/education/{educationId}")
    public @ResponseBody void deleteEducationById(@PathVariable Long educationId){
        educationService.deleteById(educationId);
    }
}
