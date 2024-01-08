package vn.hdweb.team9.service.imp;

import vn.hdweb.team9.domain.entity.Food;

import java.util.Optional;

public interface IFoodService {

    public Optional<Food> findById(Long id);


}
