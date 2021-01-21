/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mospolytech.engineering2020.fall.epprojectfall.comparator;

import java.sql.Date;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mospolytech.engineering2020.fall.epprojectfall.domain.Department;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Direction;
import mospolytech.engineering2020.fall.epprojectfall.domain.Employee;
import mospolytech.engineering2020.fall.epprojectfall.domain.Position;
import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;

public final class EmployeeComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Employee>> map = new HashMap<>();

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(Employee::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(Employee::getId)
                                                           .reversed());
   
        map.put(new Key("firstName", Direction.asc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getFirstName() == null){
                    employee.setFirstName("");
                }
                return employee.getFirstName();
            })
        );
        map.put(new Key("firstName", Direction.desc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getFirstName() == null){
                    employee.setFirstName("");
                }
                return employee.getFirstName();
            }).reversed()
        );
        
        map.put(new Key("lastName", Direction.asc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getLastName() == null){
                    employee.setLastName("");
                }
                return employee.getLastName();
            })
        );
        map.put(new Key("lastName", Direction.desc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getLastName() == null){
                    employee.setLastName("");
                }
                return employee.getLastName();
            }).reversed()
        );
        
        map.put(new Key("patronymic", Direction.asc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getPatronymic() == null){
                    employee.setPatronymic("");
                }
                return employee.getPatronymic();
            })
        );
        map.put(new Key("patronymic", Direction.desc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getPatronymic() == null){
                    employee.setPatronymic("");
                }
                return employee.getPatronymic();
            }).reversed()
        );


        map.put(new Key("hireDate", Direction.asc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getHireDate() == null){
                    employee.setHireDate(new Date(0l));
                }
                return employee.getHireDate();
            })
        );
        map.put(new Key("hireDate", Direction.desc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getHireDate() == null){
                    employee.setHireDate(new Date(0l));
                }
                return employee.getHireDate();
            }).reversed()
        );
        
        
        map.put(new Key("departmentName", Direction.asc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getStaffingTable() == null){
                    employee.setStaffingTable(new StaffingTable());
                }
                
                if(employee.getStaffingTable().getDepartment() == null){
                    employee.getStaffingTable().setDepartment(new Department());
                    employee.getStaffingTable().getDepartment().setDepartmentName("");
                }
                return employee.getStaffingTable().getDepartment().getDepartmentName();
            })
        );
        map.put(new Key("departmentName", Direction.desc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getStaffingTable() == null){
                    employee.setStaffingTable(new StaffingTable());
                }
                
                if(employee.getStaffingTable().getDepartment() == null){
                    employee.getStaffingTable().setDepartment(new Department());
                    employee.getStaffingTable().getDepartment().setDepartmentName("");
                }
                return employee.getStaffingTable().getDepartment().getDepartmentName();
            }).reversed()
        );
        
        map.put(new Key("positionName", Direction.asc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getStaffingTable() == null){
                    employee.setStaffingTable(new StaffingTable());
                }
                
                if(employee.getStaffingTable().getPosition() == null){
                    employee.getStaffingTable().setPosition(new Position());
                    employee.getStaffingTable().getPosition().setPositionName("");
                }
                return employee.getStaffingTable().getPosition().getPositionName();
            })
        );
        map.put(new Key("positionName", Direction.desc), 
            Comparator.comparing((Employee employee) -> {
                if(employee.getStaffingTable() == null){
                    employee.setStaffingTable(new StaffingTable());
                }
                
                if(employee.getStaffingTable().getPosition() == null){
                    employee.getStaffingTable().setPosition(new Position());
                    employee.getStaffingTable().getPosition().setPositionName("");
                }
                return employee.getStaffingTable().getPosition().getPositionName();
            }).reversed()
        );
    }

    public static Comparator<Employee> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private EmployeeComparators() {
    }
}