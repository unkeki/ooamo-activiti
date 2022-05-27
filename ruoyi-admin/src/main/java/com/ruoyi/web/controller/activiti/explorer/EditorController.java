package com.ruoyi.web.controller.activiti.explorer;

import com.ruoyi.system.domain.ActivitiSaveModel;
import com.ruoyi.web.util.ActivitiTracingChart;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class EditorController {

    @Resource
    private ActivitiTracingChart activitiTracingChart;

    // 流程图编辑器
    @GetMapping("editor")
    public String editor() {
        return "modeler";
    }


    @PutMapping(value = "activiti/{modelId}/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(HttpServletRequest request, @PathVariable String modelId, ActivitiSaveModel activitiSaveModel) {
        activitiSaveModel.setModelId(modelId);
        System.out.println("save it");
    }

    @GetMapping("activiti/getFlowcChart")
    public void getFlowcChart(String processInstanceId, HttpServletResponse response) throws IOException {
        activitiTracingChart.generateFlowChart(processInstanceId, response.getOutputStream());
    }
}
