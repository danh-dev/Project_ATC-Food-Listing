package vn.hdweb.team9.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.service.BlogService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    @GetMapping(value = "/blog/view")
    public  String blogView (Model model){

        List<BlogResponDto> blogs = blogService.findAll();
        model.addAttribute("blogs",blogs);

        return "client/blogs";
    }
}
