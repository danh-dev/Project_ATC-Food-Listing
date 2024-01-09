package vn.hdweb.team9.controller.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.service.BlogService;


@Controller
@RequiredArgsConstructor
@Slf4j
public class BlogController {
    private final BlogService blogService;
    @GetMapping(value = "/blog/{slug}.html")
    public String viewBlogDetail(@PathVariable String slug, Model model) {
        BlogResponDto blog = blogService.getBlogBySlug(slug);
        log.info(String.valueOf(blog));
        model.addAttribute("blog", blog);
        return "client/blog_detail";
    }
    @GetMapping("/blog_demo")
    public String showPaginatedBlogs(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 4;

        Page<BlogResponDto> blogPage = blogService.findPaginatedBlogs(page, pageSize);
        int totalPages = blogPage.getTotalPages();

        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "client/blogs";

    }

    @GetMapping("/blog_demo/{page}")
    public String showPaginatedBlogs(@PathVariable("page") int page, Model model) {
        if (page == 0) {
            return "redirect:/blog_demo";
        }
        return showPaginatedBlogs(model, page);
    }
}
