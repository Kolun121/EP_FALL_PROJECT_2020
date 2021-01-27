package mospolytech.engineering2020.fall.epprojectfall.service.springdatajpa;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import org.springframework.stereotype.Service;
import mospolytech.engineering2020.fall.epprojectfall.repository.PositionRepository;
import mospolytech.engineering2020.fall.epprojectfall.comparator.PositionComparators;
import mospolytech.engineering2020.fall.epprojectfall.service.PositionService;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Order;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Column;
import mospolytech.engineering2020.fall.epprojectfall.comparator.DepartmentComparators;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;
import org.springframework.util.ObjectUtils;


@Service
public class PositionServiceImpl implements PositionService {
    private static final Comparator<Position> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final PositionRepository positionRepository;
    
    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    
    @Override
    public Set<Position> findAll() {
        Set<Position> positions = new HashSet<>();
        positionRepository.findAll().forEach(positions::add);
        return positions;
    }

    @Override
    public Position findById(Long id) {
        Optional<Position> positionOptional = positionRepository.findById(id);
        
        if (!positionOptional.isPresent()) {
            throw new RuntimeException("Должность не найдена по id:" + id );
        }

        return positionOptional.get();
    }
    
    @Override
    public Position save(Position object) {
        return positionRepository.save(object);
    }
    
    @Override
    public void saveAll(Iterable<Position> positions) {
        positionRepository.saveAll(positions);
    }

    @Override
    public void delete(Position object) {
        positionRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        positionRepository.deleteById(id);
    }
    
    @Override
    public void deleteAll(Iterable<Position> positions) {
        positionRepository.deleteAll(positions);
    }
    
     @Override
    public Page<Position> getPositions(PagingRequest pagingRequest) {
        List<Position> positions = positionRepository.findAll();
        return getPage(positions, pagingRequest);
    }
    
     private Page<Position> getPage(List<Position> positions, PagingRequest pagingRequest) {
        List<Position> filtered = positions.stream()
                                           .sorted(sortPositions(pagingRequest))
                                           .filter(filterPositions(pagingRequest))
                                           .skip(pagingRequest.getStart())
                                           .limit(pagingRequest.getLength())
                                           .collect(Collectors.toList());

        long count = positions.stream()
                             .filter(filterPositions(pagingRequest))
                             .count();

        Page<Position> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<Position> filterPositions(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || ObjectUtils.isEmpty(pagingRequest.getSearch()
                                                                                  .getValue())) {
            return position -> true;
        }
        
     
        String value = pagingRequest.getSearch()
                                    .getValue().toLowerCase();
        
        Predicate<Position> positionPredicate = position -> 
        {
            if(position.getPositionName() == null){
                position.setPositionName("");
            }
            return position.getId().toString().contains(value) || 
                    position.getPositionName().toLowerCase().contains(value);
        };
        
        return positionPredicate;
    }

    private Comparator<Position> sortPositions(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                                       .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                                         .get(columnIndex);

            Comparator<Position> comparator = PositionComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
          
        }

        return EMPTY_COMPARATOR;
    }
}