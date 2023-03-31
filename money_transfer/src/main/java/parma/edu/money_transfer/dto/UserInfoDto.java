package parma.edu.money_transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Boolean ok;
    private String login;
    private String fullName;
    private String role;
    private Boolean enabled;
}