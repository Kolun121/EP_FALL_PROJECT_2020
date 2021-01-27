package mospolytech.engineering2020.fall.epprojectfall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Имя должно быть заполнено")
    private String firstName = "Не указано";
    @NotBlank(message = "Фамилия должна быть заполнена")
    private String lastName = "Не указано";
    @NotBlank(message = "Отчество должно быть заполнено")
    private String patronymic = "Не указано";
    
    private String email;
    private String phoneNumber;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("hireDate")
    @CreationTimestamp
    private Date hireDate;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    private Passport passport;
    
    @ManyToOne
    @JoinColumn(name = "staffing_table_id")
    private StaffingTable staffingTable;
    
    
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "employee")
    private List<Education> educations = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "employee")
    private List<Family> familyMembers = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "employee")
    private List<JobHistory> jobHistoryList = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "employee")
    private List<Vacation> vacationsList = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Employee)) {
            return false;
        }
        
        Employee employee = (Employee) o;
        
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(patronymic, employee.patronymic);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstName, id);
    }
}
