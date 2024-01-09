package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.hdweb.team9.domain.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
}
