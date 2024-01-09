package vn.hdweb.team9.controller.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.dto.request.BlogRepuestDto;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.domain.entity.Blog;
import vn.hdweb.team9.repository.interfaces.IBlogRepository;
import vn.hdweb.team9.service.BlogService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
@RequiredArgsConstructor
public class BlogAdminController {
    private final BlogService blogService;
    private final IBlogRepository blogRepository;

    @GetMapping(value = {"", "/","/list"})
    public String writeBlogNew(Model model){
        List<BlogResponDto> blogs = blogService.findAll();
        model.addAttribute("blogs",blogs);
        return "admin/blog_list";
    }
    @GetMapping(value = "/edit/{blogId}")
    public String editBlog (@PathVariable Long blogId, Model model){
        Blog blog = blogRepository.findBlogById(blogId);
        BlogRepuestDto blogRepuestDto = new BlogRepuestDto();
        blogRepuestDto.setBlogId(blog.getId());
        blogRepuestDto.setBlog_title(blog.getBlogTitle());
        blogRepuestDto.setBlog_content(blog.getBlogContent());
        model.addAttribute("blog",blogRepuestDto);
        model.addAttribute("current_img", blog.getBlog_img());
        return "admin/edit_blog";
    }
    @GetMapping (value = "/create")
    public String create(Model model) {
        model.addAttribute("blog", new BlogRepuestDto());
        return "admin/create_blog";
    }
    @PostMapping(value = "/create")
    public String createBlog (@ModelAttribute BlogRepuestDto blogRepuestDto, RedirectAttributes ra){
        List<String> validContentTypes = Arrays.asList("image/jpeg", "image/png");
        if(!validContentTypes.contains(blogRepuestDto.getBlog_img().getContentType())){
            ra.addFlashAttribute("error_upload", "Ảnh không đúng định dạng.");
            return "redirect:/admin/blogs/create";
        }
        if(blogRepuestDto.getBlog_img().getSize() > 5000000){
            ra.addFlashAttribute("error_upload", "Kích thước ảnh không được vượt quá 5MB.");
            return "redirect:/admin/blogs/create";
        }
        if(blogService.checkExistBlogTitle(blogRepuestDto.getBlog_title())){
            ra.addFlashAttribute("error_title_exist", "Blog with title "+blogRepuestDto.getBlog_title()+" existed");
            return "redirect:/admin/blogs/create";

        }
        blogService.createBlog(blogRepuestDto);
        ra.addFlashAttribute("success_upload", "Create a new blog success.");
        return "redirect:/admin/blogs/list";
    }
    @PostMapping(value = "/update")
    public String updateBlog (@ModelAttribute BlogRepuestDto blogRepuestDto,
                 RedirectAttributes ra)
    {
        if(blogRepuestDto.getBlog_img() != null && !blogRepuestDto.getBlog_img().isEmpty()) {
            List<String> validContentTypes = Arrays.asList("image/jpeg", "image/png");
            if(!validContentTypes.contains(blogRepuestDto.getBlog_img().getContentType())){
                ra.addFlashAttribute("error_upload", "Ảnh không đúng định dạng.");
                return "redirect:/admin/blogs/edit/"+blogRepuestDto.getBlogId();
            }
            if(blogRepuestDto.getBlog_img().getSize() > 5000000){
                ra.addFlashAttribute("error_upload", "Kích thước ảnh không được vượt quá 5MB.");
                return "redirect:/admin/blogs/edit/"+blogRepuestDto.getBlogId();
            }
        }

        blogService.blogUpdate(blogRepuestDto);
        ra.addFlashAttribute("success_upload", "Edit blog success.");
        return "redirect:/admin/blogs/list";
    }

}
