package mospolytech.engineering2020.fall.epprojectfall.domain.paging;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {

    private Integer column;
    private Direction dir;

}