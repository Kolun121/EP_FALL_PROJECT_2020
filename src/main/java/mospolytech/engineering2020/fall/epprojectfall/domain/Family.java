package mospolytech.engineering2020.fall.epprojectfall.domain;

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
@Table(name = "family")
public class Family implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String patronymic;
    private String relation;
    private Date birthDate;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Family)) {
            return false;
        }
        
        Family family = (Family) o;
        
        return Objects.equals(id, family.id) &&
                Objects.equals(firstName, family.firstName) &&
                Objects.equals(lastName, family.lastName) &&
                Objects.equals(patronymic, family.patronymic);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstName, id);
    }
}