package com.ooamo.web.controller.monitor;

import com.ooamo.common.core.controller.BaseController;
import com.ooamo.framework.web.domain.Server;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 服务器监控
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

    @RequiresPermissions("monitor:server:view")
    @GetMapping()
    public String server(ModelMap mmap) throws Exception
    {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        String prefix = "monitor/server";
        return prefix + "/server";
    }
}
