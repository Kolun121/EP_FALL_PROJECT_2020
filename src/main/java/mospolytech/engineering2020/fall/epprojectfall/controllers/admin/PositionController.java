package mospolytech.engineering2020.fall.epprojectfall.controllers.admin;

import java.util.List;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import mospolytech.engineering2020.fall.epprojectfall.service.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("adminPositionController")
@RequestMapping("/admin/positions")
public class PositionController {
    private final PositionService positionService;
    
    
    public PositionController(
            PositionService positionService
            ) 
    {
        this.positionService = positionService;
    }
    
    @GetMapping
    public String getPositionPage(Model model) {
        return "admin/position/listPositions";
    }
    
    @PostMapping("/new")
    @ResponseBody
    public String newPosition(Model model){
        Position newPosition = new Position();
        
        positionService.save(newPosition);

        return "redirect:/admin/positions";
    }
    
    @PostMapping("/update")
    @ResponseBody
    public String updatePosition(@RequestBody List<Position> listPositions){

        positionService.saveAll(listPositions);

        return "redirect:/admin/positions";
    }
    
    
    @PostMapping
    @ResponseBody
    public Page<Position> lisPositionsAjax(@RequestBody PagingRequest pagingRequest) {

        return positionService.getPositions(pagingRequest);
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteDepartment(@RequestBody List<Position> listPositions){
 

        positionService.deleteAll(listPositions);

        return "redirect:/admin/positions";
    }
}
