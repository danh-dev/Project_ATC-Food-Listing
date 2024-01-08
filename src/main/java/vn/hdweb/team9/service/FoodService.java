package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.repository.interfaces.IFoodRepository;
import vn.hdweb.team9.service.imp.IFoodService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService implements IFoodService {

    private final IFoodRepository foodRepository;
    public Optional<Food> findById(Long id) {
        return foodRepository.findById(id);
    }
}
