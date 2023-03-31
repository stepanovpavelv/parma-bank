package parma.edu.reporting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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