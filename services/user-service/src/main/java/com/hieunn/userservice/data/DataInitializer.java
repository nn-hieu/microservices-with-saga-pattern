package com.hieunn.userservice.data;

import com.hieunn.userservice.entities.User;
import com.hieunn.userservice.entities.Wallet;
import com.hieunn.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            log.info("Starting data initialization...");

            User user1 = new User();
            user1.setUsername("user1");
            user1.setPassword(passwordEncoder.encode("123"));

            Wallet wallet1 = new Wallet();
            wallet1.setBalance(100000);
            wallet1.setUser(user1);
            user1.setWallet(wallet1);

            log.info("Created user 1: username = {}, password = 123, balance = {}",
                    user1.getUsername(),
                    user1.getWallet().getBalance());

            User user2 = new User();
            user2.setUsername("user2");
            user2.setPassword(passwordEncoder.encode("123"));

            Wallet wallet2 = new Wallet();
            wallet2.setBalance(200000);
            wallet2.setUser(user2);
            user2.setWallet(wallet2);

            log.info("Created user 2: username = {}, password = 123, balance = {}",
                    user2.getUsername(),
                    user2.getWallet().getBalance());

            User user3 = new User();
            user3.setUsername("user3");
            user3.setPassword(passwordEncoder.encode("123"));

            Wallet wallet3 = new Wallet();
            wallet3.setBalance(300000);
            wallet3.setUser(user3);
            user3.setWallet(wallet3);

            log.info("Created user 3: username = {}, password = 123, balance = {}",
                    user3.getUsername(),
                    user3.getWallet().getBalance());

            userRepository.saveAll(Arrays.asList(user1, user2, user3));

            log.info("Finished data initialization...");
        }
    }
}
