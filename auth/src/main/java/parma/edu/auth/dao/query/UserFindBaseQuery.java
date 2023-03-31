package parma.edu.auth.dao.query;

import parma.edu.auth.dao.enums.UserField;
import parma.edu.auth.dao.enums.UserTable;
import parma.edu.auth.model.User;

import java.util.Map;

public abstract class UserFindBaseQuery implements DatabaseQuery {
    @Override
    public String getTemplate() {
        return
                "SELECT " +
                        "  t1." + UserField.ID.getField() + ", " +
                        "  t1." + UserField.LOGIN.getField() + ", " +
                        "  t1." + UserField.PASSWORD.getField() + ", " +
                        "  t1." + UserField.ROLE.getField() + ", " +
                        "  t1." + UserField.FULL_NAME.getField() + ", " +
                        "  t1." + UserField.IS_ENABLED.getField() + ", " +
                        "  t1." + UserField.LOCKED.getField() + " " +
                        " FROM " + UserTable.USERS.getTable() + " AS t1 ";
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of();
    }

    @Override
    public Object getObject(Map<String, Object> queryForMap) {
        User record = new User();
        record.setId((Integer)queryForMap.get(UserField.ID.getField()));
        record.setLogin((String)queryForMap.get(UserField.LOGIN.getField()));
        record.setPassword((String)queryForMap.get(UserField.PASSWORD.getField()));
        record.setFullName((String)queryForMap.get(UserField.FULL_NAME.getField()));
        record.setEnabled((Boolean)queryForMap.get(UserField.IS_ENABLED.getField()));
        record.setLocked((Boolean)queryForMap.get(UserField.LOCKED.getField()));
        record.setRole((String)queryForMap.get(UserField.ROLE.getField()));
        return record;
    }
}