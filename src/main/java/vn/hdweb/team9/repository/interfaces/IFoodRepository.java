package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.hdweb.team9.domain.entity.Food;

import java.util.List;
import java.util.Optional;

public interface IFoodRepository {
    List<Food> findAll();

    Optional<Food> findById(Long foodId);

}
