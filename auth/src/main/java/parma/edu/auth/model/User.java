package parma.edu.auth.model;


import lombok.Getter;
import lombok.Setter;

/**
 * Модель пользователя системы.
 */
@Getter
@Setter
public class User {
    private Integer id;
    private String login;
    private String password;
    private String fullName;
    private String role;
    private boolean isEnabled;
    private boolean locked;
}