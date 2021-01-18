package mospolytech.engineering2020.fall.epprojectfall.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;

public interface PositionRepository extends CrudRepository<Position, Long>{
    List<Position> findAll();
}
