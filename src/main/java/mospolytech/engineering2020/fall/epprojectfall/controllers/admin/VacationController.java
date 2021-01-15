package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.sql.Date;
import java.util.Arrays;
import mospolytech.engineering2020.fall.epprojectfall.service.VacationService;
import mospolytech.engineering2020.fall.epprojectfall.domain.Vacation;
import mospolytech.engineering2020.fall.epprojectfall.domain.enumeration.VacationType;
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


@Controller("adminVacationController")
@RequestMapping("/admin/employees")
public class VacationController {
    private final EmployeeService employeeService;
    private final VacationService vacationService;
    
    public VacationController(VacationService vacationService, EmployeeService employeeService) {
        this.vacationService = vacationService;
        this.employeeService = employeeService;
    }
    
    @GetMapping("{id}/vacation") 
    public String getVacationByEmployeeId(@PathVariable String id, Model model) {
        
        model.addAttribute("employeeId", id);
        model.addAttribute("vacations", vacationService.findAll());
        
        return "admin/vacation/updateVacation";
    }
    
    @PostMapping("{id}/vacation/new")
    public ModelAndView createVacationItem(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/fragments/employee/employeeVacationItem :: employeeVacationItem ");
        Vacation vacationItem = new Vacation();
        vacationItem.setEmployee(employeeService.findById(id));
        mav.addObject("vacationItems", Arrays.asList(vacationService.save(vacationItem)));
        return mav;
    }
    
    @PostMapping("{employeeId}/vacation/{vacationId}")
    public @ResponseBody void updateVacationById(
            @RequestParam(value = "vacationType", required = false) String vacationType, 
            @RequestParam(value = "startDate", required = false) String startDate, 
            @RequestParam(value = "endDate", required = false) String endDate, 
            @PathVariable Long employeeId, 
            @PathVariable Long vacationId)
    {
        Vacation vacationItem = new Vacation();
        
        vacationItem.setId(vacationId);
        vacationItem.setVacationType(VacationType.valueOf(vacationType));
        
        if(!startDate.isEmpty()){
            vacationItem.setStartDate(Date.valueOf(startDate));
        }

        if(!endDate.isEmpty()){
            vacationItem.setEndDate(Date.valueOf(endDate));
        }

        vacationItem.setEmployee(employeeService.findById(employeeId));
        vacationService.save(vacationItem);
    }
    
    @DeleteMapping("{employeeId}/vacation/{vacationId}")
    public @ResponseBody void deleteVacationById(@PathVariable Long vacationId){
        vacationService.deleteById(vacationId);
    }
}
