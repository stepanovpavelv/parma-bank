package parma.edu.auth.mapper;

import org.springframework.stereotype.Component;
import parma.edu.auth.dto.CreateRequestDto;
import parma.edu.auth.dto.UserResponseDto;
import parma.edu.auth.model.User;
import parma.edu.auth.system.security.ApplicationUser;
import parma.edu.auth.system.security.Role;

/**
 * Кастомный маппер для сущности `Пользователь`.
 * @apiNote В данном проекте не используется сторонняя библиотека, т.к. во всем приложении кастится всего 1 класс.
 */
@Component
public class AuthMapper {

    public UserResponseDto toDto(ApplicationUser appUser) {
        if (appUser == null) {
            return null;
        }

        UserResponseDto response = new UserResponseDto();
        response.setFullName(appUser.getFullName());
        response.setLogin(appUser.getLogin());
        response.setRole(appUser.getRole().name());
        response.setEnabled(appUser.isEnabled());
        return response;
    }

    public UserResponseDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDto response = new UserResponseDto();
        response.setFullName(user.getFullName());
        response.setLogin(user.getLogin());
        response.setRole(user.getRole());
        response.setEnabled(user.isEnabled());
        return response;
    }

    public ApplicationUser toUserDetails(User user) {
        if (user == null) {
            return null;
        }

        ApplicationUser appUser = new ApplicationUser();
        appUser.setId(user.getId());
        appUser.setLogin(user.getLogin());
        appUser.setPassword(user.getPassword());
        appUser.setFullName(user.getFullName());
        appUser.setLocked(user.isLocked());
        appUser.setEnabled(user.isEnabled());
        appUser.setRole(Role.valueOf(user.getRole()));
        return appUser;
    }

    public User toEntity(CreateRequestDto request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole().name());
        user.setEnabled(true);
        return user;
    }
}