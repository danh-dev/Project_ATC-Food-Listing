//package vn.hdweb.team9.repository;
//
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import vn.hdweb.team9.domain.entity.SiteSetting;
//import vn.hdweb.team9.repository.interfaces.ISiteSettingRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//@RequiredArgsConstructor
//public class SiteSettingRepository implements ISiteSettingRepository {
//
//    private final EntityManager em;
//
//    @Override
//    public Optional<SiteSetting> findById(Long id) {
//        return Optional.ofNullable(em.find(SiteSetting.class, id));
//    }
//
//    @Override
//    public Optional<SiteSetting> findByKey(String key) {
//        return Optional.ofNullable(em.createQuery("select s from SiteSetting s where s.key = :key", SiteSetting.class).
//                setParameter("key", key).getSingleResult());
//    }
//
//    @Override
//    public void delete(SiteSetting siteSetting) {
//        em.remove(siteSetting);
//    }
//
//    @Override
//    public void save(SiteSetting siteSetting) {
//        if (siteSetting.getId() == null) {
//            em.persist(siteSetting);
//        }
//        em.merge(siteSetting);
//    }
//
//    @Override
//    public List<SiteSetting> findAll() {
//        return em.createQuery("select * from SiteSetting s").getResultList();
//    }
//}
