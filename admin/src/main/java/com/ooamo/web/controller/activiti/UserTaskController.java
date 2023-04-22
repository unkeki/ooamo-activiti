package com.ooamo.web.controller.activiti;

import com.ooamo.common.core.domain.AjaxResult;
import com.ooamo.common.core.domain.entity.SysUser;
import com.ooamo.system.domain.FlowForm;
import com.ooamo.system.domain.Form;
import com.ooamo.system.domain.Purchase;
import com.ooamo.system.service.IFlowFormService;
import com.ooamo.system.service.IFormService;
import com.ooamo.system.service.ISysUserService;
import com.ooamo.system.service.IUserTaskService;
import com.ooamo.system.vo.UserTaskVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ooamo.common.utils.ShiroUtils.getSysUser;

@Controller
@RequestMapping("/userTask")
public class UserTaskController {

    private String prefix = "activiti/public";

    @Autowired
    private IUserTaskService userTaskService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IFormService formService;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Autowired
    private IFlowFormService flowFormService;

    @ApiOperation(value = "为任务选择待办人页面")
    @GetMapping("/add")
    public String add(ModelMap mmap, @RequestParam("pdId") String pdId)
    {
        SysUser user = getSysUser();
        List<SysUser> userList = userService.selectUserList(new SysUser());
        List<Form> formList = formService.selectFormList(new Form());
        List<UserTaskVO> userTasks = userTaskService.findUserTaskByPdId(pdId);
        mmap.put("pdId", pdId);
        mmap.put("user", user);
        mmap.put("userList", userList);
        mmap.put("formList", formList);
        mmap.put("userTasks", userTasks);
        return prefix + "/add";
    }

    @ApiOperation(value = "流程办理页面")
    @GetMapping("/manage")
    public String manage(String taskid, ModelMap mmap)
    {
        Task t = taskService.createTaskQuery().taskId(taskid).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            FlowForm flowForm = flowFormService.selectFlowFormById(p.getId());
            Long formId = flowForm.getFormId();
            Form form = formService.selectFormById(formId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mmap.put("applyTime", sdf.format(flowForm.getApplytime()));
            mmap.put("applyer", flowForm.getApplyer());
            mmap.put("taskid", taskid);
            mmap.put("content", form.getContent());
            mmap.put("taskName", t.getName());
        }
        return prefix + "/manage";
    }

    @ApiOperation(value = "通过流程定义Id获取用户任务")
    @GetMapping("/list")
    @ResponseBody
    public List<UserTaskVO> findUserTaskByPdId(String pId){
        return userTaskService.findUserTaskByPdId(pId);
    }

    @ApiOperation(value = "为每个任务添加用户")
    @PostMapping("/addUser2Task")
    @ResponseBody
    public AjaxResult addUser2Task(HttpServletRequest request, @RequestParam("pdId") String pdId){

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> taskNameAssigneeMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            taskNameAssigneeMap.put(entry.getKey(), entry.getValue()[0]);
        }
        userTaskService.addUser2Task(taskNameAssigneeMap, pdId);
        return AjaxResult.success();

    }
}
