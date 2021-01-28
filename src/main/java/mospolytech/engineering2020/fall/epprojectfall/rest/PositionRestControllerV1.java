package mospolytech.engineering2020.fall.epprojectfall.rest;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.service.PositionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/positions")
public class PositionRestControllerV1 {
    private final PositionService positionService;
    
    public PositionRestControllerV1(
            PositionService positionService
    ){
        this.positionService = positionService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Position position = positionService.findById(id);

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(position, HttpStatus.OK);
    }
    @GetMapping 
    public ResponseEntity<Set<Position>> getAllPositions() {
        Set<Position> positions = positionService.findAll();

        if (positions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(positions, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Position> savePosition(@RequestBody Position position) {
        HttpHeaders headers = new HttpHeaders();

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        positionService.save(position);
        
        return new ResponseEntity<>(position, headers, HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Position> updatePosition(@RequestBody @Valid Position position) {
        HttpHeaders headers = new HttpHeaders();

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        positionService.save(position);

        return new ResponseEntity<>(position, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Position> deletePosition(@PathVariable("id") Long id) {
        Position position = positionService.findById(id);

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        positionService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

 
}
