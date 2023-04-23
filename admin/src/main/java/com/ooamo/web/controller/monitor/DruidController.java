package com.ooamo.web.controller.monitor;

import com.ooamo.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController extends BaseController {

    @RequiresPermissions("monitor:data:view")
    @GetMapping()
    public String index()
    {
        String prefix = "/druid";
        return redirect(prefix + "/index.html");
    }
}
