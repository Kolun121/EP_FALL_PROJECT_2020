package mospolytech.engineering2020.fall.epprojectfall.domain;

import javax.persistence.*;
import lombok.Data;
import mospolytech.engineering2020.fall.epprojectfall.domain.enumeration.Role;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
}