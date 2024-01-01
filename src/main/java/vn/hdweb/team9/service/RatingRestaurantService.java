package vn.hdweb.team9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.repository.interfaces.RatingRestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingRestaurantService {
    private final RatingRestaurantRepository ratingRestaurantRepository;

    @Autowired
    public RatingRestaurantService(RatingRestaurantRepository ratingRestaurantRepository) {
        this.ratingRestaurantRepository = ratingRestaurantRepository;
    }


    // create
    public RatingRestaurant createRatingRestaurant(RatingRestaurant ratingRestaurant) {
        return ratingRestaurantRepository.save(ratingRestaurant);
    }

    // get by Id
    public Optional<RatingRestaurant> getRatingRestaurantById(Long id) {
        return ratingRestaurantRepository.findById(id);
    }


    // getAll
    public List<RatingRestaurant> getAllRatingRestaurants() {
        return ratingRestaurantRepository.findAll();
    }

    // update
    public RatingRestaurant updateRatingRestaurant(Long id, RatingRestaurant updatedRatingRestaurant) {
        if (ratingRestaurantRepository.existsById(id)) {
            updatedRatingRestaurant.setId(id);
            return ratingRestaurantRepository.save(updatedRatingRestaurant);
        }
        return null;
    }

    // delete
    public void deleteRatingRestaurantById(Long id) {
        ratingRestaurantRepository.deleteById(id);
    }

}
