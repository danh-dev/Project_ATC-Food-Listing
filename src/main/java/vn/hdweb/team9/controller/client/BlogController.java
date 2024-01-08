package vn.hdweb.team9.controller.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
