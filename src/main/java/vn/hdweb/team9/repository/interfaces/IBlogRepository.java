package vn.hdweb.team9.repository.interfaces;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Blog;
@Repository
public interface IBlogRepository extends JpaRepository<Blog, Long> {
    Blog findBlogById(Long id);
    Blog findBlogBySlug (String slug);

    Blog findBlogByBlogTitle (String blog_title);

}
