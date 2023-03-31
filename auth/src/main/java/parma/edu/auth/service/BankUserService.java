package parma.edu.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import parma.edu.auth.dao.repository.UserRepository;
import parma.edu.auth.dto.UserResponseDto;
import parma.edu.auth.mapper.AuthMapper;
import parma.edu.auth.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankUserService implements UserDetailsService {
    private final AuthMapper mapper;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> userOpt = userRepository.findByLogin(username);
            return userOpt.map(mapper::toUserDetails).orElseThrow();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Пользователь " + username + " не найден", e);
        }
    }

    public UserResponseDto loadUserById(Integer id) throws UsernameNotFoundException {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            return userOpt.map(mapper::toDto).orElseThrow();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Пользователь c id=" + id + " не найден", e);
        }
    }
}