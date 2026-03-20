package egen5208.gym.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import egen5208.gym.dto.Login.LoginRequest;
import egen5208.gym.model.User;
import egen5208.gym.repo.UserRepo;
import egen5208.gym.utils.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Optional<String> login(LoginRequest loginRequest) {
        Optional<User> user = userRepo.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPasswordHash())) {
            return Optional.empty();
        }
        return Optional.of(jwtUtils.generateToken(user.get()));
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateJwtToken(token);
    }
}
