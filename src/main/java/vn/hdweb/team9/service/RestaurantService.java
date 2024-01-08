package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.controller.admin.RestaurantForm;
import vn.hdweb.team9.domain.dto.respon.RestaurantDto;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;
import vn.hdweb.team9.utility.UploadFile;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RestaurantService {
    private final IRestaurantRepository restaurantRepository;

    public RestaurantService(IRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void add(RestaurantForm r) throws FileUploadException {
        Restaurant res = new Restaurant();
//        if (restaurantRepository.findByName(r.getRestaurantName())){
//
//        }
        res.setRestaurantName(r.getRestaurantName());
        res.setAddress(r.getAddress());
        res.setDescription(r.getDescription());
        res.setImage(UploadFile.uploadFile(r.getImage()));
        res.setSlug(r.getSlug());
        res.setOpenTime(r.getOpenTime());
        res.setCloseTime(r.getCloseTime());
        //res.setActive(r.isActive());
        // if not duplication, saving member

        restaurantRepository.save(res);
        // return the id of member
    }

//    public boolean validateDuplicateMember(String restaurantName) {
//        return restaurantRepository.findByRestaurantName(restaurantName);
//    }


    public List<Restaurant> findRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findOne(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }
}
