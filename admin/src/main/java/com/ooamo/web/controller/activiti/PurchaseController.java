package com.ooamo.web.controller.activiti;

import com.ooamo.common.annotation.Log;
import com.ooamo.common.core.controller.BaseController;
import com.ooamo.common.core.domain.AjaxResult;
import com.ooamo.common.core.domain.entity.SysUser;
import com.ooamo.common.core.page.TableDataInfo;
import com.ooamo.common.enums.BusinessType;
import com.ooamo.common.utils.poi.ExcelUtil;
import com.ooamo.system.domain.Form;
import com.ooamo.system.domain.Purchase;
import com.ooamo.system.service.IFormService;
import com.ooamo.system.service.IPurchaseService;
import com.ooamo.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 采购Controller
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseController
{
    private final String prefix = "activiti/purchase";

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private ISysUserService userService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Autowired
    private IFormService formService;

    @GetMapping()
    public String purchase()
    {
        return prefix + "/purchase";
    }

    @ApiOperation("查询采购列表")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Purchase purchase)
    {
        startPage();
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        return getDataTable(list);
    }

    @ApiOperation("导出采购列表")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Purchase purchase)
    {
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        return util.exportExcel(list, "采购数据");
    }

    /**
     * 新增采购
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        SysUser user = getSysUser();
        List<SysUser> userList = userService.selectUserList(new SysUser());
        List<Form> formList = formService.selectFormList(new Form());
        mmap.put("user", user);
        mmap.put("userlist", userList);
        mmap.put("formList", formList);
        return prefix + "/add";
    }

    @ApiOperation("新增保存采购")
    @Log(title = "采购", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Purchase purchase)
    {
        purchase.setApplytime(new Date());
        return toAjax(purchaseService.insertPurchase(purchase));
    }

    @ApiOperation("编辑采购")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(Purchase purchase)
    {
        return toAjax(purchaseService.updatePurchase(purchase));
    }

    @ApiOperation("删除采购")
    @Log(title = "采购", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(purchaseService.deletePurchaseByIds(ids));
    }

    /**
     * 采购经理审批
     */
    @GetMapping("/purchasemanager")
    public String purchasemanager(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            Purchase apply = purchaseService.selectPurchaseById(Long.parseLong(p.getBusinessKey()));
            Long formId = apply.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(apply.getApplytime()));
            mmap.put("apply", apply);
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
        }
        return prefix + "/purchasemanager";
    }

    /**
     * 财务审批
     */
    @GetMapping("/finance")
    public String finance(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            Purchase apply = purchaseService.selectPurchaseById(Long.parseLong(p.getBusinessKey()));
            Long formId = apply.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(apply.getApplytime()));
            mmap.put("apply", apply);
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
        }
        return prefix + "/finance";
    }

    /**
     * 总经理审批
     */
    @GetMapping("/manager")
    public String manager(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            Purchase apply = purchaseService.selectPurchaseById(Long.parseLong(p.getBusinessKey()));
            Long formId = apply.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(apply.getApplytime()));
            mmap.put("apply", apply);
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
        }
        return prefix + "/manager";
    }

    /**
     * 出纳付款
     */
    @GetMapping("/pay")
    public String pay(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            Purchase apply = purchaseService.selectPurchaseById(Long.parseLong(p.getBusinessKey()));
            Long formId = apply.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(apply.getApplytime()));
            mmap.put("apply", apply);
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
        }
        return prefix + "/pay";
    }

    /**
     * 收货确认
     */
    @GetMapping("/receiveitem")
    public String receiveitem(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            Purchase apply = purchaseService.selectPurchaseById(Long.parseLong(p.getBusinessKey()));
            Long formId = apply.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(apply.getApplytime()));
            mmap.put("apply", apply);
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
        }
        return prefix + "/receiveitem";
    }

    /**
     * 调整申请
     */
    @GetMapping("/updateapply")
    public String updateapply(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            Purchase apply = purchaseService.selectPurchaseById(Long.parseLong(p.getBusinessKey()));
            Long formId = apply.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(apply.getApplytime()));
            mmap.put("apply", apply);
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
        }
        return prefix + "/updateapply";
    }
}
