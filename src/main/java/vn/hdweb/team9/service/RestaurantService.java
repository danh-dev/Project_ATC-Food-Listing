package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.dto.RestaurantForm;
import vn.hdweb.team9.domain.dto.UpdateRestaurantForm;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;
import vn.hdweb.team9.utility.TitleToSlug;
import vn.hdweb.team9.utility.UploadFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final IRestaurantRepository restaurantRepository;

    public void add(RestaurantForm r) throws FileUploadException {
        // Kiểm tra xem nhà hàng có tên tương tự đã tồn tại hay chưa
//        log.info("Input restaurant: " + r.getRestaurantName());
        List<Restaurant> existingRestaurants = restaurantRepository.findByRestaurantName(r.getRestaurantName());
//        log.info("Found restaurant: " + existingRestaurants.get(0).getRestaurantName());
        if (!existingRestaurants.isEmpty()) {
            // Nếu tồn tại, bạn có thể thực hiện xử lý khi có lỗi, ví dụ:
            throw new DuplicateRestaurantNameException("Restaurant with the same name already exists");
        }

        // Nếu không có lỗi, tiếp tục thêm nhà hàng mới
        Restaurant res = new Restaurant();
        res.setRestaurantName(r.getRestaurantName());
        res.setAddress(r.getAddress());
        res.setDescription(r.getDescription());
        res.setImage(UploadFile.uploadFile(r.getImage()));
        res.setSlug(createSlug(r.getRestaurantName()) );
        res.setOpenTime(r.getOpenTime());
        res.setCloseTime(r.getCloseTime());

        restaurantRepository.save(res);
        // return the id of member
    }

    private String createSlug(String restaurantName) {
        while (true) {
            String slug = TitleToSlug.toSlug(restaurantName);
            if (restaurantRepository.findBySlug(slug)== null) {
                return slug;
            }
            restaurantName = restaurantName + "1";
        }
    }

    public void update(Long restaurantId, UpdateRestaurantForm updatedRestaurantForm) throws FileUploadException {
        // Kiểm tra xem nhà hàng có tồn tại hay không
        Optional<Restaurant> existingRestaurantOptional = restaurantRepository.findById(restaurantId);
        if (existingRestaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        Restaurant existingRestaurant = existingRestaurantOptional.get();

        // Kiểm tra xem tên đã tồn tại cho những nhà hàng khác hay không
        if (!existingRestaurant.getRestaurantName().equals(updatedRestaurantForm.getRestaurantName())) {
            List<Restaurant> existingRestaurantsWithNewName = restaurantRepository.findByRestaurantName(updatedRestaurantForm.getRestaurantName());
            if (!existingRestaurantsWithNewName.isEmpty()) {
                throw new DuplicateRestaurantNameException("Restaurant with the updated name already exists");
            }
        }

        // Cập nhật thông tin của nhà hàng
        existingRestaurant.setRestaurantName(updatedRestaurantForm.getRestaurantName());
        existingRestaurant.setAddress(updatedRestaurantForm.getAddress());
        existingRestaurant.setDescription(updatedRestaurantForm.getDescription());
        existingRestaurant.setSlug(createSlug(updatedRestaurantForm.getRestaurantName()));
        existingRestaurant.setOpenTime(updatedRestaurantForm.getOpenTime());
        existingRestaurant.setCloseTime(updatedRestaurantForm.getCloseTime());

        // Cập nhật hình ảnh nếu có
        if (updatedRestaurantForm.getImage() != null) {
            existingRestaurant.setImage(UploadFile.uploadFile(updatedRestaurantForm.getImage()));
        }

        // Lưu những thay đổi vào cơ sở dữ liệu
        restaurantRepository.save(existingRestaurant);
    }

    public List<Restaurant> findRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findOne(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public static class DuplicateRestaurantNameException extends RuntimeException {
        public DuplicateRestaurantNameException(String message) {
            super(message);
        }
    }

    public static class RestaurantNotFoundException extends RuntimeException {
        public RestaurantNotFoundException(String message) {
            super(message);
        }
    }
    public Optional<Restaurant> findBySlug (String slug) {
        return Optional.ofNullable(restaurantRepository.findBySlug(slug));
    }
    
    // get all restaurants by food name
    public Set<Restaurant> findAllRestaurantsWithFoodName(String foodName) {
        return restaurantRepository.FindAllWithFoodNameQuery(foodName);
    }
}
