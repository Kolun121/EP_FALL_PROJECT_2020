package mospolytech.engineering2020.fall.epprojectfall.controllers;


import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    
    @GetMapping
    public String getEmployeesPage(Model model) {

        return "user/employee/index";
    }
    
    @PostMapping
    @ResponseBody
    public Page<Employee> listEmployeesAjax(@RequestBody PagingRequest pagingRequest) {

        return employeeService.getEmployees(pagingRequest);
    }
    
}
