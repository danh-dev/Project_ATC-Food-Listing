package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.hdweb.team9.domain.dto.respon.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "full_name" , nullable = false)
    private String fullName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name= "avatar")
    private String avatar;
    @Column(name="create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> listOrder = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RatingFood> listRatingFood = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RatingRestaurant> listRatingRestaurant = new ArrayList<>();


    public UserDto convertUserDto(){
        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setFullName(this.fullName);
        userDto.setEmail(this.email);
        userDto.setPhone(this.phone);
        userDto.setAddress(this.address);
        userDto.setAvatar(this.avatar);
        userDto.setCreatedAt(this.createdAt);
        userDto.setRole(this.role.getRoleName());
        return userDto;
    }
}
