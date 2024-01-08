package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.controller.admin.RestaurantForm;
import vn.hdweb.team9.controller.admin.UpdateRestaurantForm;
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
        // Kiểm tra xem nhà hàng có tên tương tự đã tồn tại hay chưa
        List<Restaurant> existingRestaurants = restaurantRepository.findByRestaurantName(r.getRestaurantName());
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
        res.setSlug(r.getSlug());
        res.setOpenTime(r.getOpenTime());
        res.setCloseTime(r.getCloseTime());

        restaurantRepository.save(res);
        // return the id of member
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
        existingRestaurant.setSlug(updatedRestaurantForm.getSlug());
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
}
