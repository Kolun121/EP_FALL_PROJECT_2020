package mospolytech.engineering2020.fall.epprojectfall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passports")
public class Passport implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String passportID;
    private String issuedBy;
    private Date issueDate;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Passport)) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id &&
                Objects.equals(passportID, issueDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(passportID, id);
    }
}
