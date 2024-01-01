package vn.hdweb.team9.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.hdweb.team9.domain.entity.Role;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IRoleRepository;
import vn.hdweb.team9.repository.interfaces.IUserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(IRoleRepository roleRepository, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0) {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setRoleName("USER");
            roleRepository.save(roleUser);
        }
        if(userRepository.count() == 0) {
            User newAdmin = new User();
            newAdmin.setEmail("admin@hdweb.vn");
            newAdmin.setPassword(passwordEncoder.encode("123@321@"));
            newAdmin.setFullName("Admin");
            newAdmin.setRole(roleRepository.findByRoleName("ADMIN"));
            userRepository.save(newAdmin);

            User newUser = new User();
            newUser.setEmail("user@hdweb.vn");
            newUser.setPassword(passwordEncoder.encode("123@321@"));
            newUser.setFullName("User");
            newUser.setRole(roleRepository.findByRoleName("USER"));
            userRepository.save(newUser);
        }
    }
}
