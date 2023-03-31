package parma.edu.reporting.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ActivityHistory {

    private Integer id;
    private Integer operationId;
    private Integer operationTypeId;
    private Integer userSourceId;
    private String userSourceLogin;
    private Integer userTargetId;
    private String userTargetLogin;
    private Integer accountSourceId;
    private Integer accountTargetId;
    private Integer operationStatusId;
    private ZonedDateTime date;
    private ZonedDateTime updateDate;
    private double amount;
}