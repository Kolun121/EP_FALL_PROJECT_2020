package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.util.ArrayList;
import java.util.Arrays;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import org.springframework.util.ObjectUtils;

import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.PassportService;
import mospolytech.engineering2020.fall.epprojectfall.service.StaffingTableService;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.Passport;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.domain.item.StaffingTableItem;
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
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PassportService passportService;
    private final StaffingTableService staffingTableService;
    
    
    public EmployeeController(
            EmployeeService employeeService, 
            PassportService passportService,
            StaffingTableService staffingTableService
            ) 
    {
        this.employeeService = employeeService;
        this.passportService = passportService;
        this.staffingTableService = staffingTableService;
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
        if (result.hasErrors()) {
            System.out.println(result.getFieldErrors());
            return "admin/employee/updateEmployee";
        } else {
            employee.setId(Long.parseLong(id));
            employee.getPassport().setEmployee(employee);
            System.out.println(employee.getStaffingTable());
            employeeService.save(employee);
            return "redirect:/admin/employees/" + id;
        }
    }
    
    @GetMapping("/{id}/staffingTablesAjax")
    @ResponseBody
    public List<StaffingTableItem> staffingTableItems(@PathVariable String id, @RequestParam(value = "q", required = false) String query) {

        if (ObjectUtils.isEmpty(query)) {
            
            return staffingTableService.findAll().stream()
                         .limit(15)
                         .map(this::mapToStaffingTableItem)
                         .collect(Collectors.toList());
        }

        return staffingTableService.findAll().stream()
                    .filter(
                    (StaffingTable staffingTable) -> {
                        if(staffingTable.getDepartment() == null){
                            staffingTable.setDepartment(new Department());
                            staffingTable.getDepartment().setDepartmentName("");
                        }

                        if(staffingTable.getPosition() == null){
                            staffingTable.setPosition(new Position());
                            staffingTable.getPosition().setPositionName("");
                        }

                        return staffingTable.getDepartment().getDepartmentName().contains(query) || 
                            staffingTable.getPosition().getPositionName().contains(query);

                    })
                    .limit(15)
                    .map((StaffingTable staffingTable) -> {
                            return mapToStaffingTableItem(staffingTable);
                    })
                    .collect(Collectors.toList());
    }

    private StaffingTableItem mapToStaffingTableItem(StaffingTable staffingTable) {
        if(staffingTable.getDepartment() == null){
            staffingTable.setDepartment(new Department());
            staffingTable.getDepartment().setDepartmentName("Не указан");
        }

        if(staffingTable.getPosition() == null){
            staffingTable.setPosition(new Position());
            staffingTable.getPosition().setPositionName("Не указана");
        }

        return StaffingTableItem.builder()
                        .id(staffingTable.getId())
                        .text("Должность: " 
                                + staffingTable.getPosition().getPositionName() 
                                + " - Отдел: " 
                                + staffingTable.getDepartment().getDepartmentName()) 
                        .build();
    }
    
    @PostMapping("/new")
    public @ResponseBody String newEmployee(Model model){
        Passport newPassport = new Passport();
        Employee newEmployee = new Employee();
        
        newPassport.setEmployee(newEmployee);
        newEmployee.setPassport(newPassport);
        
        Employee savedEmployee = employeeService.save(newEmployee);

        return savedEmployee.getId().toString();
    }
    
}
