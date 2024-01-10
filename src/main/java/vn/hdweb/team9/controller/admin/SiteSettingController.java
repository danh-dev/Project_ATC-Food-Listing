package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.hdweb.team9.domain.entity.SiteSetting;
import vn.hdweb.team9.service.SiteSettingService;

@Controller
@RequestMapping("/admin/setting")
@RequiredArgsConstructor
public class SiteSettingController {
    private final SiteSettingService siteSettingService;

    @PostMapping
    public void add(@RequestBody SiteSetting siteSetting) {
        siteSettingService.add(siteSetting);
    }

}
