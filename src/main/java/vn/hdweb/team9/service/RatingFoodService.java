package vn.hdweb.team9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.repository.interfaces.RatingFoodRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingFoodService {
    private final RatingFoodRepository ratingFoodRepository;

    @Autowired
    public RatingFoodService(RatingFoodRepository ratingFoodRepository) {
        this.ratingFoodRepository = ratingFoodRepository;
    }

    // create
    public RatingFood createRatingFood(RatingFood ratingFood) {
        return ratingFoodRepository.save(ratingFood);
    }

    // get by Id
    public Optional<RatingFood> getRatingFoodById(Long id) {
        return ratingFoodRepository.findById(id);
    }

    // get all
    public List<RatingFood> getAllRatingFood (){
        return ratingFoodRepository.findAll();
    }

    // update
    public RatingFood updateRatingFood(Long id, RatingFood ratingFoodUpdate) {
        boolean exists = ratingFoodRepository.existsById(id);
        if (exists) {
            return ratingFoodRepository.save(ratingFoodUpdate);
        } else {
            return null;
        }
    }

    //delete
    public void deleteById(Long id){
        ratingFoodRepository.deleteById(id);
    }

}
