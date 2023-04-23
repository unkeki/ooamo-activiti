package com.ooamo.web.controller.activiti.explorer;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EditorController {

    /** 流程图编辑器
     */
    @ApiOperation(value = "流程图编辑器")
    @GetMapping("editor")
    public String editor() {
        return "modeler";
    }

}
