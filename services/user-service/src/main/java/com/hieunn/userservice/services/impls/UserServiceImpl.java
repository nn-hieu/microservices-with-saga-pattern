package com.hieunn.userservice.services.impls;

import com.hieunn.commonlib.dtos.users.UserDto;
import com.hieunn.commonlib.exceptions.NotFoundException;
import com.hieunn.userservice.entities.User;
import com.hieunn.userservice.exceptions.InsufficientFundsException;
import com.hieunn.userservice.mappers.UserMapper;
import com.hieunn.userservice.repositories.UserRepository;
import com.hieunn.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto.Response register(UserDto.CreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        log.info("Register new user with username: {}", user.getUsername());

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(noRollbackFor = InsufficientFundsException.class)
    public UserDto.Response deductBalance(Integer userId, Integer amount) {
        if (amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", userId);

                    return new NotFoundException("User not found with id: " + userId);
                });

        if (user.getWallet().getBalance() < amount) {
            log.error("Insufficient funds");

            throw new InsufficientFundsException();
        }

        user.getWallet().setBalance(user.getWallet().getBalance() - amount);
        userRepository.save(user);

        log.info("Deduct balance of user id: {} with amount: {}", userId, amount);

        return userMapper.toResponse(user);
    }
}
