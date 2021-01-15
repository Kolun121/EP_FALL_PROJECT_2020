package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import javax.validation.Valid;
import mospolytech.engineering2020.fall.epprojectfall.controllers.*;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
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


@Controller("adminEmployeesController")
@RequestMapping("/admin/employees")
public class EmployeesController {
    private final EmployeeService employeeService;
    
    
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    
    @GetMapping
    public String getEmployeesPage(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {
        model.addAttribute("employees", employeeService.getPage(pageNumber, size));
        return "admin/employee/index";
    }
    
    @GetMapping("{id}") 
    public String getEmployeeById(@PathVariable String id, Model model) {
        Employee employee = employeeService.findById(Long.parseLong(id));

        model.addAttribute("employee", employee);
        return "admin/employee/updateEmployee";
    }
    
    @PostMapping("{id}") 
    public String updateEmployeeById(@PathVariable String id, @Valid Employee employee, BindingResult result) {
        employeeService.save(employee);
        return "redirect:/admin/employees/" + id;
    }
    
    @PostMapping("/new")
    public @ResponseBody String newCourse(Model model){
        Employee newEmployee = new Employee();
        
        Employee savedEmployee = employeeService.save(newEmployee);

        return savedEmployee.getId().toString();
    }
    
}
