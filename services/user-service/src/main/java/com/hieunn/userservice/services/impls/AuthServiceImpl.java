package com.hieunn.userservice.services.impls;

import com.hieunn.commonlib.dtos.auths.LoginDto;
import com.hieunn.commonlib.exceptions.NotFoundException;
import com.hieunn.commonlib.exceptions.UnauthorizedException;
import com.hieunn.commonlib.utils.JwtUtils;
import com.hieunn.userservice.entities.User;
import com.hieunn.userservice.repositories.UserRepository;
import com.hieunn.userservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginDto.Response login(LoginDto.Request request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found with username {}", request.getUsername());

                    return new NotFoundException("User not found with username: " + request.getUsername());
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Incorrect username or password");

            throw new UnauthorizedException("Incorrect username or password");
        }

        String token = jwtUtils.generateToken(
                user.getUsername(),
                Map.of("userId", user.getId())
        );

        LoginDto.Response response = new LoginDto.Response();
        response.setToken(token);

        return response;
    }
}
