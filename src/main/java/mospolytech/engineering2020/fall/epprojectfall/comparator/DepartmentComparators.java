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
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;

public final class DepartmentComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Department>> map = new HashMap<>();

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(Department::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(Department::getId)
                                                           .reversed());

        map.put(new Key("departmentName", Direction.asc), Comparator.comparing(Department::getDepartmentName));
        map.put(new Key("departmentName", Direction.desc), Comparator.comparing(Department::getDepartmentName)
                                                                 .reversed());

    }

    public static Comparator<Department> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private DepartmentComparators() {
    }
}