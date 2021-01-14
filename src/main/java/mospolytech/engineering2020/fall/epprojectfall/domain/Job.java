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
@Table(name = "jobs")
public class Job implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private Long salary;
    private Integer employeesNumber = 0;
    
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @OneToMany(cascade = CascadeType.MERGE , mappedBy = "job")
    private List<Employee> employees = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(name = "job_task",
        joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Set<Task> tasks = new HashSet<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Job)) {
            return false;
        }
        
        Job job = (Job) o;
        
        return Objects.equals(id, job.id) &&
                Objects.equals(jobTitle, job.jobTitle);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, id);
    }
}
