package parma.edu.auth.dao.query;

import lombok.RequiredArgsConstructor;
import parma.edu.auth.dao.enums.UserField;
import parma.edu.auth.dao.enums.UserTable;

import java.util.Map;

@RequiredArgsConstructor
public class UserInsertQuery implements DatabaseQuery {
    private final Map<String, Object> insertParameters;

    @Override
    public String getTemplate() {
        return "INSERT INTO " + UserTable.USERS.getTable() +
                " (" + prepareQueryFields("") + ") " +
                " VALUES(" + prepareQueryFields(":") + "); ";
    }

    @Override
    public Map<String, Object> getParameters() {
        return insertParameters;
    }

    @Override
    public Object getObject(Map<String, Object> queryForMap) {
        return null;
    }

    private String prepareQueryFields(String prefix) {
        return prefix + UserField.LOGIN.getField() + ", " +
               prefix + UserField.PASSWORD.getField() + ", " +
               prefix + UserField.FULL_NAME.getField() + ", " +
               prefix + UserField.IS_ENABLED.getField() + ", " +
               prefix + UserField.ROLE.getField() + ", " +
               prefix + UserField.LOCKED.getField() + " ";
    }
}