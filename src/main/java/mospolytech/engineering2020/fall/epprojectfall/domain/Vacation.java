package mospolytech.engineering2020.fall.epprojectfall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import mospolytech.engineering2020.fall.epprojectfall.domain.enumeration.VacationType;

@Getter
@Setter
@Entity
@Table(name = "vacations")
public class Vacation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private VacationType vacationType;
    private Date startDate;
    private Date endDate;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Vacation)) {
            return false;
        }
        
        Vacation vacation = (Vacation) o;
        
        return Objects.equals(id, vacation.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
