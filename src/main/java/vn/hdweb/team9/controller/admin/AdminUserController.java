package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.service.imp.IUserService;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final IUserService userService;



    @RequestMapping(value = {"", "/","/list"})
    public String usersList(Model model) {

        List<UserDto> users = userService.findAll();
        /*order id user and reverse*/
        users.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));

        model.addAttribute("users", users);

        return "admin/users_list";
    }
}
