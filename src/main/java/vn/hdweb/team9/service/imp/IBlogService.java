package vn.hdweb.team9.service.imp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.hdweb.team9.domain.dto.request.BlogRepuestDto;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.domain.entity.Blog;

import java.util.List;

public interface IBlogService {
    void createBlog (BlogRepuestDto blogDto);

    List<BlogResponDto> findAll();

    List<BlogResponDto> findLimitOrderDate();

    void blogUpdate (BlogRepuestDto blogRepuestDto);

    boolean checkExistBlogTitle (String blog_title);
    String createSlug (String blog_title);

    BlogResponDto getBlogBySlug(String slug);

    Page<BlogResponDto> findPaginatedBlogs(int page, int size);
}
