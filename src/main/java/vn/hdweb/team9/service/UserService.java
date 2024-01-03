package vn.hdweb.team9.service;

import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
