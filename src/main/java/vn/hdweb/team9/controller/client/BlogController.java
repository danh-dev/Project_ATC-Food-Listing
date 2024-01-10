package vn.hdweb.team9.controller.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.service.BlogService;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/blog/detail/{slug}")
    public String viewBlogDetail(@PathVariable String slug, Model model) {
        BlogResponDto blog = blogService.getBlogBySlug(slug);
        model.addAttribute("blog", blog);
        return "client/blog_detail";
    }

    @GetMapping("/blog_demo")
    public String showPaginatedBlogs(Model model, @RequestParam(defaultValue = "0") int page) {

        int pageSize = 4;

        Page<BlogResponDto> blogPage = blogService.findPaginatedBlogs(page, pageSize);
        List<BlogResponDto> blogsRandom = blogService.getRandomBlogs();
        int totalPages = blogPage.getTotalPages();

        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("blogsRandom", blogsRandom);
        log.info(blogPage.getContent().toString());
        return "client/blogs";
    }

    @GetMapping("/blog_demo/{page}")
    public String showPaginatedBlogs(@PathVariable("page") int page, Model model) {
        if (page < 1) {
            return "redirect:/blog_demo/1";
        }
        return showPaginatedBlogs(model, page - 1);

    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam("searchText") String searchText) {
        // Xử lý tìm kiếm và trả về URL với tham số
        log.info("return redirect:/blogs/search/results?searchText=" + URLEncoder.encode(searchText, StandardCharsets.UTF_8));
        return "redirect:/blogs/search/results?searchText=" + URLEncoder.encode(searchText, StandardCharsets.UTF_8);
    }

    @GetMapping("/blogs/search/results")
    public String showSearchResults(@RequestParam("searchText") String searchText, Model model) {
        List<BlogResponDto> searchResults = blogService.searchBlogs(URLDecoder.decode(searchText, StandardCharsets.UTF_8));
        model.addAttribute("searchResults", searchResults);
        return "client/blogs_search";
    }
}
