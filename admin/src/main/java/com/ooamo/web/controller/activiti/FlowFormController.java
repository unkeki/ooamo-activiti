package com.ooamo.web.controller.activiti;

import com.ooamo.system.domain.FlowForm;
import com.ooamo.system.domain.Form;
import com.ooamo.system.service.IFlowFormService;
import com.ooamo.system.service.IFormService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flowForm")
public class FlowFormController {

    @Autowired
    private IFlowFormService flowFormService;
    @Autowired
    private IFormService formService;

    @ApiOperation("获取单个流程表单内容")
    @GetMapping("/getFlowFormContentById/{pId}")
    @ResponseBody
    public String getFlowFormContentById(@PathVariable String pId){

        FlowForm flowForm = flowFormService.selectFlowFormById(pId);
        return flowForm.getFormContent();

    }


    @ApiOperation("获取单个流程表单名字")
    @GetMapping("/getFlowFormName/{pId}")
    @ResponseBody
    public String getFlowFormName(@PathVariable String pId){

        FlowForm flowForm = flowFormService.selectFlowFormById(pId);
        Long formId = flowForm.getFormId();
        Form form = formService.selectFormById(formId);
        return form.getName();

    }
}
