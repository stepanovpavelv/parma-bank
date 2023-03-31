package parma.edu.money_transfer.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "operation_status_history")
public class OperationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_history_gen")
    @SequenceGenerator(name = "status_history_gen", sequenceName = "operation_status_history_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private OperationStatus status;

    @CreationTimestamp
    private ZonedDateTime date;
}