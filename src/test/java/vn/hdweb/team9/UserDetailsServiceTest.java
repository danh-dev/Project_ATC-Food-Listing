package vn.hdweb.team9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDetailsServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "user@hdweb.vn"; // replace with your actual username
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(username, userDetails.getUsername(), "Username should match");
        System.out.println("Password: " + userDetails);
        System.out.println("Result: " + passwordEncoder().matches("123@321@", userDetails.getPassword()));
    }
}