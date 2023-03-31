package parma.edu.reporting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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