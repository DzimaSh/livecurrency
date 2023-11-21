package com.example.service;

import com.example.entity.User;
import com.example.exception.MaximumUsersReachedException;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import com.example.util.Constants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Setter
@RequiredArgsConstructor
public class UserService {

    private final Constants constants;
    private final AtomicLong userCounter = new AtomicLong();
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        long usersAmount = getUsersAmount();
        userCounter.set(constants.getMaxUsers() - usersAmount);
    }

    @Transactional
    public User registerUser(User user) throws MaximumUsersReachedException {
        userCounter.set(userCounter.decrementAndGet());
        if (userCounter.get() < 0) {
            throw new MaximumUsersReachedException("Maximum users reached");
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("There's no username " + username + "registered"));
    }

    @Transactional(readOnly = true)
    public long getUsersAmount() {
        return userRepository.count();
    }
}
