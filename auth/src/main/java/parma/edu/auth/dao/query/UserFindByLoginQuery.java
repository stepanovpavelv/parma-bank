package parma.edu.auth.dao.query;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import parma.edu.auth.dao.enums.UserField;

import java.util.Map;

@RequiredArgsConstructor
public class UserFindByLoginQuery extends UserFindBaseQuery {
    private final String login;

    @Override
    public String getTemplate() {
        String template = super.getTemplate();

        if (StringUtils.hasLength(login)) {
            template += " WHERE " + UserField.LOGIN.getField() + " = :" + UserField.LOGIN.getField() + ";";
        }

        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(UserField.LOGIN.getField(), login);
    }
}