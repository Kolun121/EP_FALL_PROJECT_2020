package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.service.DepartmentService;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("adminDepartmentController")
@RequestMapping("/admin/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    
    public DepartmentController(
            DepartmentService departmentService
            ) 
    {
        this.departmentService = departmentService;
    }
    
    @GetMapping
    public String getDepartmentPage(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "admin/department/listDepartments";
    }
    
    @PostMapping("/new")
    @ResponseBody
    public String newDepartment(Model model){
        Department newDepartment = new Department();
        
        departmentService.save(newDepartment);

        return "redirect:/admin/departments";
    }
    
    @PostMapping("/update")
    @ResponseBody
    public String updateDepartment(@RequestBody List<Department> listDepartments){

        departmentService.saveAll(listDepartments);

        return "redirect:/admin/departments";
    }
    
    
    @PostMapping
    @ResponseBody
    public Page<Department> listDepartmentsAjax(@RequestBody PagingRequest pagingRequest) {

        return departmentService.getDepartments(pagingRequest);
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteDepartment(@RequestBody List<Department> listDepartments){
 

        departmentService.deleteAll(listDepartments);

        return "redirect:/admin/departments";
    }
}
