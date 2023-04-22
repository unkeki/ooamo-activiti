package com.ooamo.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ooamo.common.core.controller.BaseController;
import com.ooamo.common.core.domain.AjaxResult;
import com.ooamo.common.core.domain.entity.SysUser;
import com.ooamo.common.utils.StringUtils;
import com.ooamo.framework.shiro.service.SysRegisterService;
import com.ooamo.system.service.ISysConfigService;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@Controller
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(SysUser user)
    {
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
