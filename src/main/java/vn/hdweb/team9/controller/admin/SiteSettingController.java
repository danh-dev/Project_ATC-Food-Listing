package vn.hdweb.team9.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.dto.SiteSettingForm;
import vn.hdweb.team9.domain.entity.SiteSetting;
import vn.hdweb.team9.service.SiteSettingService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/site-setting")
@RequiredArgsConstructor
public class SiteSettingController {
    private final SiteSettingService siteSettingService;

    @GetMapping
    public String siteSettingDashboard(Model model) {
        List<SiteSetting> siteSetting = siteSettingService.findAll();
        model.addAttribute("siteSetting", siteSetting);
        model.addAttribute("siteSettingForm", new SiteSettingForm());
        return "/admin/site-setting";
    }

    @PostMapping
    public String addSiteSetting(@Valid @ModelAttribute("siteSettingForm") SiteSettingForm siteSettingForm,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/site-setting";
        }

        SiteSetting siteSetting = new SiteSetting();
        siteSetting.setKey(siteSettingForm.getKey());
        siteSetting.setValue(siteSettingForm.getValue());

        try {

        } catch (DuplicateKeyException e) {
            result.rejectValue("key", "duplicate.key", "Từ khóa đã tồn tại");
            redirectAttributes.addFlashAttribute("siteSettingForm", siteSettingForm);
            return "admin/site-setting";
        }
        siteSettingService.add(siteSetting);

        return "redirect:/admin/site-setting";

    }

}
