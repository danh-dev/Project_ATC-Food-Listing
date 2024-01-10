package vn.hdweb.team9.controller.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Slf4j
public class BlogAdminController {
    private final BlogService blogService;
    private final IBlogRepository blogRepository;

    @GetMapping(value = {"","/"})
    public String showBlogsAdmin(Model model,@RequestParam(defaultValue = "0") int page){
        int pageSize = 5;

        // Fetch paginated blogs
        Page<BlogResponDto> blogPage = blogService.findPaginatedBlogs(page, pageSize);

        // Set model attributes
        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());

        // Render template
        return "admin/blog_list";
    }

    @GetMapping("/{page}")
    public String showBlogsPagination(@PathVariable("page") int page, Model model) {

        // Fetch paginated blogs
        Page<BlogResponDto> blogPage = blogService.findPaginatedBlogs(page, 5);
        if(page<0) {
            return "redirect:admin/blog_list";
        }
        // Set model attributes
        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());

        // Render template
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
        return "redirect:/admin/blogs";
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
        return "redirect:/admin/blogs";
    }


    @PostMapping("/search")
    public String search(@RequestParam("searchText") String searchText, Model model) {
        List<BlogResponDto> searchResults = blogService.searchBlogs(searchText);
        model.addAttribute("searchResults", searchResults);
        return "admin/blogs_search";
    }


    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes ra) {
        blogService.deleteBlogById(id);
        ra.addFlashAttribute("success_delete", "Bạn đã vừa xóa thành công một blog.");
        return "redirect:/admin/blogs";
    }
}
