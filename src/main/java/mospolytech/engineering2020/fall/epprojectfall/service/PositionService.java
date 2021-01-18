package mospolytech.engineering2020.fall.epprojectfall.service;

import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;


public interface PositionService extends CrudService<Position, Long> {
    Page<Position> getPositions(PagingRequest pagingRequest);
    void saveAll(Iterable<Position> departments);
    void deleteAll(Iterable<Position> departments);
}