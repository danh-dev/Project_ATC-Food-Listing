package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Role;
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    //Get role by role name
    Role findByRoleName(String roleName);
}
