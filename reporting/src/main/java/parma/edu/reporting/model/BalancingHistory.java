package parma.edu.reporting.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class BalancingHistory {

    private Integer id;
    private Integer bankAccountId;
    private Integer userId;
    private String userLogin;
    private ZonedDateTime date;
    private double amount;
}