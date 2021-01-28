package mospolytech.engineering2020.fall.epprojectfall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "educations")
public class Education implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String specialty;
    private String qualification;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date graduationDate;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Education)) {
            return false;
        }
        Education education = (Education) o;
        return Objects.equals(id, education.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(specialty, id);
    }
    
}
