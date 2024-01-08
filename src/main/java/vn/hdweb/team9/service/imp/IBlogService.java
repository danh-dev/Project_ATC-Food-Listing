package vn.hdweb.team9.service.imp;

import vn.hdweb.team9.domain.dto.request.BlogRepuestDto;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;

import java.util.List;

public interface IBlogService {
    void createBlog (BlogRepuestDto blogDto);

    List<BlogResponDto> findAll();

    BlogResponDto findBySlug();
    void blogUpdate (BlogRepuestDto blogRepuestDto);

    boolean checkExistBlogTitle (String blog_title);
}
