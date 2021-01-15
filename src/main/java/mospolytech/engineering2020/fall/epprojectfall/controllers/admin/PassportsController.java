package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import javax.validation.Valid;
import mospolytech.engineering2020.fall.epprojectfall.controllers.*;
import mospolytech.engineering2020.fall.epprojectfall.service.PassportService;
import mospolytech.engineering2020.fall.epprojectfall.domain.Passport;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("adminPassportsController")
@RequestMapping("/admin/employees")
public class PassportsController {
    private final EmployeeService employeeService;
    private final PassportService passportService;
    
    
    public PassportsController(EmployeeService employeeService, PassportService passportService) {
        this.employeeService = employeeService;
        this.passportService = passportService;
    }

    @GetMapping("{id}/passport") 
    public String getPassportByEmployeeId(@PathVariable String id, Model model) {
        Passport passport = passportService.findByEmployeeId(Long.parseLong(id));

        model.addAttribute("passport", passport);
        return "admin/passport/updatePassport";
    }
    
    @PostMapping("{id}/passport") 
    public String savePassport(@PathVariable String id, @Valid Passport passport, BindingResult result) {
        Passport currentPassport = passportService.findByEmployeeId(Long.parseLong(id));
        passport.setId(currentPassport.getId());
        
        passportService.save(passport);
        return "redirect:/admin/employees/" + id + "/passport";
    }

    
}
