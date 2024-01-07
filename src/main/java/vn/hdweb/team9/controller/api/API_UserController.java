package vn.hdweb.team9.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IUserRepository;

import java.util.List;

@RestController
public class API_UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/api/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Login success");
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser() {
        List<User> users = userRepository.findAll();
        return new  ResponseEntity<>(users, HttpStatus.OK);
    }


}
