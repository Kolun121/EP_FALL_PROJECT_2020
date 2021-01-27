package mospolytech.engineering2020.fall.epprojectfall.controllers;


import mospolytech.engineering2020.fall.epprojectfall.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.service.StaffingTableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/staffing")
public class StaffingTableController {
    private final StaffingTableService staffingTableService;
   
    public StaffingTableController(StaffingTableService staffingTableService) {
        this.staffingTableService = staffingTableService;
    }
    
    
    @GetMapping
    public String getStaffingTablesPage(Model model) {

        return "user/staffing_table/index";
    }
    
@PostMapping
    @ResponseBody
    public Page<StaffingTable> listStaffingTablesAjax(@RequestBody PagingRequest pagingRequest) {

        return staffingTableService.getStaffingTables(pagingRequest);
    }
    
}
