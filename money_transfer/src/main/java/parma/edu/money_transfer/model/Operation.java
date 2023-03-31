package parma.edu.money_transfer.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_gen")
    @SequenceGenerator(name = "operation_gen", sequenceName = "operations_id_seq", allocationSize = 1)
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

    @CreationTimestamp
    private ZonedDateTime date;

    private double amount;

    @UpdateTimestamp
    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToMany(mappedBy = "operation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperationStatusHistory> history;
}