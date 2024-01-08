package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.User;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    //Check if user exists
    boolean existsByEmail(String email);

    //Get user by email
    User findByEmail(String email);

}
