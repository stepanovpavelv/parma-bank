package parma.edu.auth.dao.parameter;

import org.springframework.stereotype.Component;
import parma.edu.auth.model.User;
import parma.edu.auth.dao.enums.UserField;

import java.util.HashMap;
import java.util.Map;

@Component
public class AddUserParameterResolver implements ParameterResolver<User> {
    @Override
    public Map<String, Object> getQueryParameters(User request) {
        return new HashMap<>() {{
           put(UserField.LOGIN.getField(), request.getLogin());
           put(UserField.PASSWORD.getField(), request.getPassword());
           put(UserField.FULL_NAME.getField(), request.getFullName());
           put(UserField.ROLE.getField(), request.getRole());
           put(UserField.IS_ENABLED.getField(), request.isEnabled());
           put(UserField.LOCKED.getField(), request.isLocked());
        }};
    }
}