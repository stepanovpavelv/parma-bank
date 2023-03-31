package parma.edu.reporting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "operations")
public class Operation {

    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_source_id", nullable = false)
    private BankAccount accountSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_target_id")
    private BankAccount accountTarget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType type;

    private ZonedDateTime date;

    private double amount;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToMany(mappedBy = "operation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperationStatusHistory> history;
}