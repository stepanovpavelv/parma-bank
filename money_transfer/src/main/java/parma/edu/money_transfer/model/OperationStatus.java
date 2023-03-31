package parma.edu.money_transfer.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "operation_statuses")
public class OperationStatus {

    @Id
    private Integer id;

    private String name;
}