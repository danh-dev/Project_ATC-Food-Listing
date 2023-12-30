package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> listUser = new ArrayList<>();
}
