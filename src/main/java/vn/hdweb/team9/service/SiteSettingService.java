package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.SiteSetting;
import vn.hdweb.team9.repository.interfaces.ISiteSettingRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class SiteSettingService {

    private final ISiteSettingRepository siteSettingRepository;

    /**
     * Add new information
     */
    public void add(SiteSetting siteSetting) {
        Optional<SiteSetting> foundSiteSetting = findByKey(siteSetting.getKey());
        if (!foundSiteSetting.isEmpty()) {
            throw new DuplicateKeyException("This key has existed, please choose another key");
        }
        siteSettingRepository.save(siteSetting);
    }

    /**
     * Edit information
     */
    public void edit(SiteSetting siteSetting) {

    }

    /**
     * Delete information
     */
    public void deleteByKey(String key) {
        Optional<SiteSetting> siteSetting = siteSettingRepository.findByKey(key);
        siteSettingRepository.delete(siteSetting.get());
    }


    /**
     * Find by key
     */
    public Optional<SiteSetting> findByKey(String key) {
        return siteSettingRepository.findByKey(key);
    }

    /**
     * Find all
     */
    public List<SiteSetting> findAll() {
        return siteSettingRepository.findAll();
    }
}
