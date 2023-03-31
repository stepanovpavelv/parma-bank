package parma.edu.reporting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "account_details")
public class AccountDetails {

    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Column(name = "actual_date", nullable = false)
    private ZonedDateTime actualDate;

    @Column(name = "amount", nullable = false)
    private double amount;
}