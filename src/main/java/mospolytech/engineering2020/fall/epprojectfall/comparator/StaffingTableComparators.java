/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mospolytech.engineering2020.fall.epprojectfall.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Direction;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;

public final class StaffingTableComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<StaffingTable>> map = new HashMap<>();

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(StaffingTable::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(StaffingTable::getId)
                                                           .reversed());

        map.put(new Key("salary", Direction.asc), Comparator.comparing(StaffingTable::getSalary));
        map.put(new Key("salary", Direction.desc), Comparator.comparing(StaffingTable::getSalary)
                                                                 .reversed());

        map.put(new Key("employeesNumber", Direction.asc), Comparator.comparing(StaffingTable::getEmployeesNumber));
        map.put(new Key("employeesNumber", Direction.desc), Comparator.comparing(StaffingTable::getEmployeesNumber).reversed());
        
        map.put(new Key("departmentName", Direction.asc), 
                Comparator.comparing(staffingTable -> staffingTable.getDepartment().getDepartmentName()));
        map.put(new Key("departmentName", Direction.desc), 
                Comparator.comparing((StaffingTable staffingTable) -> staffingTable.getDepartment().getDepartmentName()).reversed());
        
        map.put(new Key("positionName", Direction.asc), 
                Comparator.comparing(staffingTable -> staffingTable.getPosition().getPositionName()));
        map.put(new Key("positionName", Direction.desc), 
                Comparator.comparing((StaffingTable staffingTable) -> staffingTable.getPosition().getPositionName()).reversed());
    }

    public static Comparator<StaffingTable> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private StaffingTableComparators() {
    }
}