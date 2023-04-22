package com.ooamo.web.controller.activiti;

import com.ooamo.common.core.controller.BaseController;
import com.ooamo.common.core.domain.AjaxResult;
import com.ooamo.common.core.domain.entity.SysUser;
import com.ooamo.common.core.page.TableDataInfo;
import com.ooamo.common.json.JSONObject;
import com.ooamo.common.utils.poi.ExcelUtil;
import com.ooamo.system.domain.Form;
import com.ooamo.system.service.IFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(value = "表单管理接口")
@Controller
@RequestMapping("/form/manage")
public class FormManageController extends BaseController {

    @Resource
    private IFormService formService;

    private String prefix = "activiti/form";


    @GetMapping("")
    public String formList() {
        return prefix + "/formList";
    }

    @ApiOperation("获取单个表单")
    @GetMapping("/get/{formId}")
    @ResponseBody
    public Form get(@PathVariable String formId){

        return formService.selectFormById(Long.parseLong(formId));
    }

    @ApiOperation("获取单个表单内容")
    @GetMapping("/getContent/{formId}")
    @ResponseBody
    public String getContent(@PathVariable String formId){

        Form form = formService.selectFormById(Long.parseLong(formId));
        return form.getContent();
    }

    @ApiOperation("查询所有表单")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo list(Form form) {

        startPage();
        List<Form> forms = formService.selectFormList(form);
        return getDataTable(forms);

    }

    /**
     * 新增表单页面
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增表单
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(Form form){

        SysUser sysUser = getSysUser();
        String userName = sysUser.getLoginName();
        form.setApplytime(new Date());
        form.setApplyer(userName);
        return toAjax(formService.insertForm(form));

    }

    /**
     * 编辑表单
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(String id, String content){

        Form form = formService.selectFormById(Long.valueOf(id));
        form.setContent(content);
        return toAjax(formService.updateForm(form));

    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult removeForm(String ids) {

        return toAjax(formService.deleteFormByIds(ids));
    }

    @PostMapping("/export")
    @ResponseBody
    public AjaxResult formExport(Form form){

        List<Form> list = formService.selectFormList(form);
        ExcelUtil<Form> util = new ExcelUtil<Form>(Form.class);
        return util.exportExcel(list, "表单数据");

    }


}
