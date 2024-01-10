package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.request.BlogRepuestDto;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.domain.entity.Blog;
import vn.hdweb.team9.repository.interfaces.IBlogRepository;
import vn.hdweb.team9.service.imp.IBlogService;
import vn.hdweb.team9.utility.TitleToSlug;
import vn.hdweb.team9.utility.UploadFile;

import java.lang.module.FindException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class BlogService implements IBlogService {
    private final IBlogRepository blogRepository;

    @Override
    public void createBlog(BlogRepuestDto blogDto) {

        try {
            Blog blog = new Blog();
            blog.setBlogTitle(blogDto.getBlog_title());
            blog.setBlogContent(blogDto.getBlog_content());
            blog.setBlog_img(UploadFile.uploadFile(blogDto.getBlog_img()));
            blog.setSlug(createSlug(blogDto.getBlog_title()));

            blogRepository.save(blog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<BlogResponDto> findAll() {
        List<Blog> blogs = blogRepository.findAll();
        List<Blog> sortedBlogs = blogs.stream()
                .sorted(Comparator.comparing(Blog::getCreatedAt).reversed())
                .toList();
        return getBlogResponDtos(sortedBlogs);
    }

    @Override
    public List<BlogResponDto> findLimitOrderDate() {
        List<Blog> blogs = blogRepository.findTop6ByOrderByCreatedAtDesc();
        return getBlogResponDtos(blogs);
    }

    private List<BlogResponDto> getBlogResponDtos(List<Blog> blogs) {
        return blogs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
            blog.setSlug(createSlug(blogRepuestDto.getBlog_title()));
            blog.setUpdatedAt(LocalDateTime.now());
            blog.setCreatedAt(LocalDateTime.now());

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

    @Override
    public String createSlug(String blog_title) {
        return TitleToSlug.toSlug(blog_title);
    }
    @Override
    public BlogResponDto getBlogBySlug (String slug) {
        try {
            Blog blog = blogRepository.findBlogBySlug(slug);
            return this.convertToDto(blog);
        } catch (Exception e) {
            throw new FindException("Blog with "+ slug + " not found");
        }
    }

    @Override
    public Page<BlogResponDto> findPaginatedBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return blogPage.map(this::convertToDto);
    }

    private BlogResponDto convertToDto(Blog blog) {
        BlogResponDto blogResponDto = new BlogResponDto();
        blogResponDto.setId(blog.getId());
        blogResponDto.setBlog_title(blog.getBlogTitle());
        blogResponDto.setBlog_content(blog.getBlogContent());
        blogResponDto.setBlog_img(blog.getBlog_img());
        blogResponDto.setSlug(blog.getSlug());
        blogResponDto.setCreatedAt(blog.getCreatedAt());
        return blogResponDto;
    }

    @Override
    public List<BlogResponDto> getRandomBlogs() {
        List<Blog> allBlogs = blogRepository.findAll();
        Collections.shuffle(allBlogs);

        int numOfBlogsToGet = Math.min(allBlogs.size(), 10);
        List<Blog> randomBlogs = allBlogs.subList(0, numOfBlogsToGet);

        return getBlogResponDtos(randomBlogs);
    }

    @Override
    public List<BlogResponDto> searchBlogs(String searchText) {
        List<Blog> blogsSearch = blogRepository.findByBlogTitleContaining(searchText);
        return getBlogResponDtos(blogsSearch);
    }

    @Override
    public void deleteBlogById(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            Blog blog = blogOptional.get();
            blogRepository.delete(blog);
        } else {
            // Xử lý khi không tìm thấy blog với ID tương ứng
            throw new NoSuchElementException("Không tìm thấy blog với ID: " + id);
        }
    }

}

