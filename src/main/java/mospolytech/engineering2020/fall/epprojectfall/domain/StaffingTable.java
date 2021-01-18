package mospolytech.engineering2020.fall.epprojectfall.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "staffing_table")
public class StaffingTable implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salary;
    private Integer employeesNumber = 0;
    
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    
    @OneToMany(cascade = CascadeType.MERGE , mappedBy = "staffingTable")
    private List<Employee> employees = new ArrayList<>();
    
    
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof StaffingTable)) {
            return false;
        }
        
        StaffingTable staffingTable = (StaffingTable) o;
        
        return Objects.equals(id, staffingTable.id) &&
                Objects.equals(salary, staffingTable.salary);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(salary, id);
    }
}
