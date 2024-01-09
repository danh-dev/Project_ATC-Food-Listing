package vn.hdweb.team9.service.imp;

import vn.hdweb.team9.domain.entity.Food;

import java.util.Optional;

public interface IFoodService {
    Optional<Food> findById(Long id);
    Optional<Food> findBySlug(String slug);
    Long saveFood(Food food);

    void deleteFood(Long id);

    void updateFood(Food food);

}
