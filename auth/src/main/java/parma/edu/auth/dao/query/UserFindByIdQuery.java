package parma.edu.auth.dao.query;

import lombok.RequiredArgsConstructor;
import parma.edu.auth.dao.enums.UserField;

import java.util.Map;

@RequiredArgsConstructor
public class UserFindByIdQuery extends UserFindBaseQuery {
    private final Integer id;

    @Override
    public String getTemplate() {
        String template = super.getTemplate();

        if (id != null) {
            template += " WHERE " + UserField.ID.getField() + " = :" + UserField.ID.getField() + ";";
        }

        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(UserField.ID.getField(), id);
    }
}