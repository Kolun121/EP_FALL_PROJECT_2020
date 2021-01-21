package mospolytech.engineering2020.fall.epprojectfall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "positions")
public class Position implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String positionName;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE , mappedBy = "position")
    private List<StaffingTable> staffingTables = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Position)) {
            return false;
        }
        
        Position position = (Position) o;
        
        return Objects.equals(id, position.id) &&
                Objects.equals(positionName, position.positionName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(positionName, id);
    }
}
