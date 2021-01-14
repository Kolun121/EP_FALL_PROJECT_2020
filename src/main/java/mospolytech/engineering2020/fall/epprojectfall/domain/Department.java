package mospolytech.engineering2020.fall.epprojectfall.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;
    private String location;
    
    @OneToMany(cascade = CascadeType.MERGE , mappedBy = "department")
    private List<Job> jobs = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Department)) {
            return false;
        }
        
        Department department = (Department) o;
        
        return Objects.equals(id, department.id) &&
                Objects.equals(departmentName, department.departmentName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(departmentName, id);
    }
}
