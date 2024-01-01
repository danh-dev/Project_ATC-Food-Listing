package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    //Registration
    User save(User user);
    //Get user by username
}
