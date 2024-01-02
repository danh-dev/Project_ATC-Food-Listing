package vn.hdweb.team9.repository.interfaces;

import vn.hdweb.team9.domain.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserRepository {
    //Registration
    User save(User user);


    List<User> findAll();
    //Get user by username

    Optional<User> findById(Long userId);

}
