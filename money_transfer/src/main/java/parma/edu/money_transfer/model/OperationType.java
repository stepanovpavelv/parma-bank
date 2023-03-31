package parma.edu.money_transfer.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "operation_types")
public class OperationType {

    @Id
    private Integer id;

    private String name;

    @Column(name = "is_expense")
    private Boolean isExpense;
}