package vn.hdweb.team9.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.hdweb.team9.domain.dto.request.SignUpDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IRoleRepository;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.service.imp.IUserService;
import vn.hdweb.team9.utility.UploadFile;

import java.util.List;

@Service
@Slf4j
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                List.of(authority));
    }

    @Override
    public User save(SignUpDto user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            log.error("Email already exists");
            throw new RuntimeException("Email already exists");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFullName(user.getFullName());
        newUser.setRole(roleRepository.findByRoleName("User"));
        return userRepository.save(newUser);
    }

    @Override
    public UserDto findByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDto userDto = new UserDto();
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setAvatar(user.getAvatar());
        userDto.setRole(user.getRole().getRoleName());
        userDto.setCreatedAt(user.getCreatedAt().toString());
        return userDto;
    }

    @Override
    public void uploadAvatar(MultipartFile avatar, String email) {
        User user = userRepository.findByEmail(email);
        try {
            if(!user.getAvatar().isEmpty()){
                UploadFile.deleteFile(user.getAvatar());
            }
            user.setAvatar(UploadFile.uploadFile(avatar));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void userUpdate(UserDto userDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }
}
