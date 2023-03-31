package parma.edu.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.auth.dao.repository.UserRepository;
import parma.edu.auth.dto.CreateRequestDto;
import parma.edu.auth.dto.UserResponseDto;
import parma.edu.auth.exception.RegisterValidationException;
import parma.edu.auth.mapper.AuthMapper;
import parma.edu.auth.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper mapper;
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto registerNewUser(CreateRequestDto request) {
        Optional<User> existed = userRepository.findByLogin(request.getLogin());
        if (existed.isPresent()) {
            throw new RegisterValidationException("Пользователь с логином " + request.getLogin() + " уже зарегистрирован");
        }

        User newUser = mapper.toEntity(request);
        newUser.setPassword(getEncodedPassword(request.getPassword()));
        userRepository.save(newUser);

        return mapper.toDto(newUser);
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}