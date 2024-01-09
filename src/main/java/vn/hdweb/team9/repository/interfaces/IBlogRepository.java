package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.domain.entity.Blog;


import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Long> {
    Blog findBlogById(Long id);

    Blog findBlogByBlogTitle (String blog_title);
    List<Blog> findTop6ByOrderByCreatedAtDesc();
    Blog findBlogBySlug(String slug);

    List<Blog> findByBlogTitleContaining(String blogTitle);

}
