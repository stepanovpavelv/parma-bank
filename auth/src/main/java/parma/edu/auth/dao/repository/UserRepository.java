package parma.edu.auth.dao.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import parma.edu.auth.dao.parameter.ParameterResolver;
import parma.edu.auth.dao.query.DatabaseQuery;
import parma.edu.auth.dao.query.UserFindByIdQuery;
import parma.edu.auth.dao.query.UserFindByLoginQuery;
import parma.edu.auth.dao.query.UserInsertQuery;
import parma.edu.auth.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DatabaseRepository databaseRepository;
    private final ParameterResolver<User> changeResolver;

    public Integer save(User user) {
        Map<String, Object> modifyConditions = changeResolver.getQueryParameters(user);
        DatabaseQuery modifyQuery = new UserInsertQuery(modifyConditions);
        return databaseRepository.update(modifyQuery);
    }

    public Optional<User> findByLogin(String login) {
        DatabaseQuery query = new UserFindByLoginQuery(login);
        return findUser(query);
    }

    public Optional<User> findById(Integer id) {
        DatabaseQuery query = new UserFindByIdQuery(id);
        return findUser(query);
    }

    private Optional<User> findUser(DatabaseQuery query) {
        try {
            User found = (User)query.getObject(databaseRepository.queryForMap(query));
            return Optional.of(found);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}