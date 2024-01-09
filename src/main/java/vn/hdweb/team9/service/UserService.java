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
import vn.hdweb.team9.domain.dto.respon.OrderListOfUserDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IRoleRepository;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.service.imp.IUserService;
import vn.hdweb.team9.utility.UploadFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            throw new UsernameNotFoundException("Không tìm thấy dữ liệu tài khoản");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                List.of(authority));
    }

    @Override
    public User save(SignUpDto user) {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email đã tồn tại");
            }
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setFullName(user.getFullName());
            newUser.setRole(roleRepository.findByRoleName("User"));
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy dữ liệu tài khoản");
        }
        return user;
    }

    @Override
    public void uploadAvatar(MultipartFile avatar, String email) {
        User user = userRepository.findByEmail(email);
        String currentAvatar = user.getAvatar();
        try {
            user.setAvatar(UploadFile.uploadFile(avatar));
            userRepository.save(user);
            if(currentAvatar != null){
                UploadFile.deleteFile(currentAvatar);
            }
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
    public boolean checkExistPassword(String password, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void changePassword(String password, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public List<OrderListOfUserDto> orderListOfUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        List<Order> orders = user.getListOrder();
        List<OrderListOfUserDto> orderListOfUserDtos = new ArrayList<>();
        for (Order order : orders )  {
            OrderListOfUserDto resultData = new OrderListOfUserDto();
            resultData.setOrderId(order.getId());
            resultData.setRestaurantName(order.getRestaurant().getRestaurantName());
            resultData.setRes_slug(order.getRestaurant().getSlug());
            resultData.setStatus(order.getStatus().toString());
            resultData.setDeliveryAddress(order.getDeliveryAddress());
            resultData.setTotalBill(String.valueOf(order.getTotalBill()));
            resultData.setRatingRestaurant(order.getRatingRestaurant());
            resultData.setCreatedAt(order.getCreatedAt());
            resultData.setUpdatedAt(order.getUpdatedAt());
            orderListOfUserDtos.add(resultData);
        }
        return orderListOfUserDtos;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto resultUser = new UserDto();
            resultUser.setId(user.getId());
            resultUser.setFullName(user.getFullName());
            resultUser.setPhone(user.getPhone());
            resultUser.setEmail(user.getEmail());
            resultUser.setAddress(user.getAddress());
            resultUser.setAvatar(user.getAvatar());
            resultUser.setRole(user.getRole().getRoleName());
            resultUser.setCreatedAt(user.getCreatedAt());
            userDtos.add(resultUser);
        }
        return userDtos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }

}
