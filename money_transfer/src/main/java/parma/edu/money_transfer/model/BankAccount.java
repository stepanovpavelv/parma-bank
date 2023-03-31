package parma.edu.money_transfer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_gen")
    @SequenceGenerator(name = "bank_gen", sequenceName = "bank_accounts_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @CreationTimestamp
    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountDetails> details;

    @OneToMany(mappedBy = "accountSource", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations;

    public BankAccount(Integer userId) {
        this.isEnabled = true;
        this.userId = userId;
    }
}