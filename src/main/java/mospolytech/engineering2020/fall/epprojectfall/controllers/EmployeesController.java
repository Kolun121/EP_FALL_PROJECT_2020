package mospolytech.engineering2020.fall.epprojectfall.controllers;


import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/employees")
public class EmployeesController {
    private final EmployeeService employeeService;
    
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    
    @GetMapping
    public String getEmployeesPage(Model model) {

        return "user/employee/index";
    }
    
//    @PostMapping
//    public Page<Employee> postIndexPage(Model model) {
//
//        return "user/index";
//    }
    
}
