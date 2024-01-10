package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.SiteSetting;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISiteSettingRepository extends JpaRepository<SiteSetting, Long> {

//    Optional<SiteSetting> findById(Long id);
    Optional<SiteSetting> findByKey(String key);
//
//    void delete(SiteSetting siteSetting);
//
//    void save(SiteSetting siteSetting);
//
//    List<SiteSetting> findAll();

    
}
