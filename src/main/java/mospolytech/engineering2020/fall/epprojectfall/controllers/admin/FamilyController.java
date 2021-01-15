package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.sql.Date;
import java.util.Arrays;
import mospolytech.engineering2020.fall.epprojectfall.service.FamilyService;
import mospolytech.engineering2020.fall.epprojectfall.domain.Family;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.FamilyService;
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


@Controller("adminFamilyController")
@RequestMapping("/admin/employees")
public class FamilyController {
    private final FamilyService familyService;
    private final EmployeeService employeeService;
    
    public FamilyController(EmployeeService employeeService, FamilyService familyService) {
        this.employeeService = employeeService;
        this.familyService = familyService;
    }
    
    @GetMapping("{id}/family") 
    public String getFamilyByEmployeeId(@PathVariable String id, Model model) {

        model.addAttribute("employeeId", id);
        model.addAttribute("familyMembers", familyService.findAll());
        
        return "admin/family/updateFamily";
    }
    
    @PostMapping("{id}/family/new")
    public ModelAndView createFamilyMember(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/fragments/employee/employeeFamilyMember :: employeeFamilyMember ");
        Family familyMember = new Family();
        familyMember.setEmployee(employeeService.findById(id));
        mav.addObject("familyMembers", Arrays.asList(familyService.save(familyMember)));
        return mav;
    }
    
    @PostMapping("{employeeId}/family/{familyId}")
    public @ResponseBody void updateFamilyMemberById(
            @RequestParam(value = "firstName", required = false) String firstName, 
            @RequestParam(value = "lastName", required = false) String lastName, 
            @RequestParam(value = "patronymic", required = false) String patronymic, 
            @RequestParam(value = "relation", required = false) String relation, 
            @RequestParam(value = "birthDate", required = false) String birthDate, 
            @PathVariable Long employeeId, 
            @PathVariable Long familyId)
    {
        Family familyMember = new Family();
        
        familyMember.setId(familyId);
        familyMember.setFirstName(firstName);
        familyMember.setLastName(lastName);
        familyMember.setPatronymic(patronymic);
        familyMember.setRelation(relation);
        
        if(!birthDate.isEmpty()){
            familyMember.setBirthDate(Date.valueOf(birthDate));
        }

        familyMember.setEmployee(employeeService.findById(employeeId));
        familyService.save(familyMember);
    }
    
    @DeleteMapping("{employeeId}/family/{familyId}")
    public @ResponseBody void deleteFamilyMemberById(@PathVariable Long familyId){
        familyService.deleteById(familyId);
    }
}
