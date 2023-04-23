package com.ooamo.web.controller.tool;

import com.ooamo.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * build 表单构建
 */
@Controller
@RequestMapping("/tool/build")
public class BuildController extends BaseController
{

    @RequiresPermissions("tool:build:view")
    @GetMapping()
    public String build()
    {
        String prefix = "tool/build";
        return prefix + "/build";
    }
}
