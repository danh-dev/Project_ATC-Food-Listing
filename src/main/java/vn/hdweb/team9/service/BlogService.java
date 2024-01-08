package vn.hdweb.team9.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.request.BlogRepuestDto;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.domain.entity.Blog;
import vn.hdweb.team9.repository.interfaces.IBlogRepository;
import vn.hdweb.team9.service.imp.IBlogService;
import vn.hdweb.team9.utility.UploadFile;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@Transactional
public class BlogService implements IBlogService {
    private final IBlogRepository blogRepository;

    @Autowired
    public BlogService(IBlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void createBlog(BlogRepuestDto blogDto) {

        try {
            Blog blog = new Blog();
            blog.setBlogTitle(blogDto.getBlog_title());
            blog.setBlogContent(blogDto.getBlog_content());
            blog.setBlog_img(UploadFile.uploadFile(blogDto.getBlog_img()));
            blog.setSlug(null);

            blogRepository.save(blog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<BlogResponDto> findAll() {
        List<Blog> blogs = blogRepository.findAll();
        List<BlogResponDto> blogResponDtoList = new ArrayList<>();
        for(Blog blog: blogs) {
            BlogResponDto blogResult = new BlogResponDto();
            blogResult.setId(blog.getId());
            blogResult.setBlog_title(blog.getBlogTitle());
            blogResult.setBlog_img(blog.getBlog_img());
            blogResult.setSlug(blog.getSlug());
            blogResult.setCreatedAt(blog.getCreatedAt());
            blogResponDtoList.add(blogResult);
        }
        return blogResponDtoList;
    }

    @Override
    public BlogResponDto findBySlug() {
        return null;
    }

    @Override
    public void blogUpdate(BlogRepuestDto blogRepuestDto) {
        Blog blog = blogRepository.findBlogById(blogRepuestDto.getBlogId());
        try {

            if (blogRepuestDto.getBlog_title() != null) {
                blog.setBlogTitle(blogRepuestDto.getBlog_title());
            }

            if (blogRepuestDto.getBlog_content() != null) {
                blog.setBlogContent(blogRepuestDto.getBlog_content());
            }

            if (blogRepuestDto.getBlog_img() != null && !blogRepuestDto.getBlog_img().isEmpty()) {
                String uploadedImage = UploadFile.uploadFile(blogRepuestDto.getBlog_img());
                blog.setBlog_img(uploadedImage);
            }

            blog.setSlug(null);

            blogRepository.save(blog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkExistBlogTitle(String blog_title) {
        Blog blog = blogRepository.findBlogByBlogTitle(blog_title);
        return blog != null;
    }

}

