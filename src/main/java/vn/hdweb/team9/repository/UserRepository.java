package vn.hdweb.team9.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IUserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager =entityManager;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
    }
    @Override
    public Optional<User> findById(Long userId) {
        User user = entityManager.find(User.class, userId);
        return Optional.ofNullable(user);
    }

}
