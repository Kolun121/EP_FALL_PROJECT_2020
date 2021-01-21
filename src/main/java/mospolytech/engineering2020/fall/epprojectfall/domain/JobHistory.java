package mospolytech.engineering2020.fall.epprojectfall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees_job_history")
public class JobHistory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private Date startDate;
    private Date endDate;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof JobHistory)) {
            return false;
        }
        
        JobHistory jobHistory = (JobHistory) o;
        
        return Objects.equals(id, jobHistory.id) &&
                Objects.equals(jobTitle, jobHistory.jobTitle);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, id);
    }
}
