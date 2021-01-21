package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.service.DepartmentService;
import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import mospolytech.engineering2020.fall.epprojectfall.service.PositionService;
import mospolytech.engineering2020.fall.epprojectfall.service.StaffingTableService;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("adminStaffingTableController")
@RequestMapping("/admin/staffing")
public class StaffingTableController {
    private final StaffingTableService staffingTableService;
    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    
    public StaffingTableController(
            StaffingTableService staffingTableService,
            PositionService positionService,
            DepartmentService departmentService,
            EmployeeService employeeService
            ) 
    {
        this.staffingTableService = staffingTableService;
        this.positionService = positionService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }
    
    @GetMapping
    public String getStaffingPage(Model model) {
        return "admin/staffing_table/index";
    }
    
    @PostMapping("/new")
    @ResponseBody
    public void newStaffingTable(Model model){
        StaffingTable newStaffingTable = new StaffingTable();
//        System.out.println(newStaffingTable.getDepartment());
        staffingTableService.save(newStaffingTable);
    }
    
    @GetMapping("{id}") 
    public String getStaffingTableById(@PathVariable String id, Model model) {
        Set<Position> positions = positionService.findAll();
        Set<Department> departments = departmentService.findAll();
        Set<Employee> employees = employeeService.findAll();
        StaffingTable staffingTable = staffingTableService.findById(Long.parseLong(id));

        model.addAttribute("staffingTable", staffingTable);
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        model.addAttribute("employeesAll", employees);
        
        return "admin/staffing_table/updateStaffingTable";
    }
    
    @PostMapping("{id}") 
    public String updateStaffingTableById(@PathVariable String id, StaffingTable staffingTable) {
            staffingTable.setId(Long.parseLong(id));
            List<Employee> employees = employeeService.findAllByStaffingTableId(Long.parseLong(id));
            for(Employee employee: employees){
                if(!staffingTable.getEmployees().contains(employee)){
                    employee.setStaffingTable(null);
                }
            }
            
            for(Employee employee: staffingTable.getEmployees()){
                employee.setStaffingTable(staffingTable);
            }
            
            
            employeeService.saveAll(employees);
            staffingTableService.save(staffingTable);
            return "redirect:/admin/staffing/" + id;
    }
    
    
    @PostMapping
    @ResponseBody
    public Page<StaffingTable> listStaffingTablesAjax(@RequestBody PagingRequest pagingRequest) {

        return staffingTableService.getStaffingTables(pagingRequest);
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteStaffingTables(@RequestBody List<StaffingTable> listStaffingTables){
        staffingTableService.deleteAll(listStaffingTables);
    }
}
