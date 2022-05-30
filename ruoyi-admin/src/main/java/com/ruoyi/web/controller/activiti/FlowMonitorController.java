package com.ruoyi.web.controller.activiti;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.FlowInfo;
import com.ruoyi.system.domain.VariableInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 流程监控
 */
@Api(value = "流程监控接口")
@Controller
@RequestMapping("/flow/monitor")
public class FlowMonitorController extends BaseController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    RepositoryService repositoryService;

    @Resource
    ProcessEngineConfiguration configuration;

    private String prefix = "activiti/monitor";

    @GetMapping("/instance")
    public String processList() {
        return prefix + "/processInstance";
    }

    @GetMapping("/history")
    public String processHistory() {
        return prefix + "/processHistory";
    }

    @GetMapping("/historyDetail")
    public String historyDetail(String processInstanceId, ModelMap mmap) {
        mmap.put("processInstanceId", processInstanceId);
        return prefix + "/processHistoryDetail";
    }

    @GetMapping("/processVariablesDetail")
    public String processVariablesDetail(String processInstanceId, ModelMap mmap) {
        mmap.put("processInstanceId", processInstanceId);
        return prefix + "/processVariablesDetail";
    }

    @ApiOperation("查询所有正在运行的流程实例列表")
    @RequestMapping(value = "/listProcess", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo getlist(@RequestParam(required = false) String bussinesskey, @RequestParam(required = false) String name,
                                 Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        ProcessInstanceQuery condition = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotEmpty(bussinesskey)) {
            condition.processInstanceBusinessKey(bussinesskey);
        }
        if (StringUtils.isNotEmpty(name)) {
            condition.processDefinitionName(name);
        }
        List<ProcessInstance> processList = condition.orderByProcessDefinitionId().desc().listPage(start, pageSize);
        int total = condition.orderByProcessDefinitionId().desc().list().size();
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcessInstanceId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setStartUserId(p.getStartUserId());
            info.setSuspended(p.isSuspended());
            info.setEnded(p.isEnded());
            // 查看当前活动任务
            List<Task> tasks =  taskService.createTaskQuery().processInstanceId(p.getProcessInstanceId()).list();
            String taskName = "";
            String assignee = "";
            for (Task t : tasks) {
                taskName += t.getName() + ",";
                assignee += t.getAssignee() + ",";
            }
            taskName = taskName.substring(0, taskName.length() -1);
            assignee = assignee.substring(0, assignee.length() -1);
            info.setCurrentTask(taskName);
            info.setAssignee(assignee);
            flows.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(flows);
        rspData.setTotal(total);
        return rspData;
    }

    @ApiOperation("查询所有流程实例列表-包含在运行和已结束")
    @RequestMapping(value = "/listHistoryProcess", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listHistoryProcess(@RequestParam(required = false) String bussinesskey, @RequestParam(required = false) String name,
                                            Integer pageSize, Integer pageNum) {
        int total = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().desc().list().size();
        int start = (pageNum - 1) * pageSize;
        HistoricProcessInstanceQuery condition = historyService.createHistoricProcessInstanceQuery();
        if (StringUtils.isNotEmpty(bussinesskey)) {
            condition.processInstanceBusinessKey(bussinesskey);
        }
        if (StringUtils.isNotEmpty(name)) {
            condition.processDefinitionName(name);
        }
        List<HistoricProcessInstance> processList = condition.orderByProcessInstanceStartTime().desc().listPage(start, pageSize);
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setEndTime(p.getEndTime());
            info.setStartUserId(p.getStartUserId());
            if (p.getEndTime() == null) {
                info.setEnded(false);
                // 查看当前活动任务
                List<Task> tasks =  taskService.createTaskQuery().processInstanceId(p.getId()).list();
                String taskName = "";
                String assignee = "";
                for (Task t : tasks) {
                    taskName += t.getName() + ",";
                    assignee += t.getAssignee() + ",";
                }
                taskName = taskName.substring(0, taskName.length() -1);
                assignee = assignee.substring(0, assignee.length() -1);
                info.setCurrentTask(taskName);
                info.setAssignee(assignee);
            } else {
                info.setEnded(true);
            }
            flows.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(flows);
        rspData.setTotal(total);
        return rspData;
    }

    @ApiOperation("查询一个流程的活动历史")
    @RequestMapping(value = "/history/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo history(@PathVariable String processInstanceId, Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        List<HistoricActivityInstance> history = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().listPage(start, pageSize);
        int total = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().list().size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(history);
        rspData.setTotal(total);
        return rspData;
    }


    @ApiOperation("查询所有正在运行的执行实例列表")
    @RequestMapping(value = "/listExecutions", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listExecutions(@RequestParam(required = false) String key, @RequestParam(required = false) String name,
                                        Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        List<Execution> executionList = runtimeService.createExecutionQuery().orderByProcessInstanceId().desc().listPage(start, pageSize);
        int total = runtimeService.createExecutionQuery().orderByProcessInstanceId().desc().list().size();
        List<FlowInfo> flows = new ArrayList<>();
        executionList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcessInstanceId());
            info.setSuspended(p.isSuspended());
            info.setEnded(p.isEnded());
            flows.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(flows);
        rspData.setTotal(total);
        return rspData;
    }

    @ApiOperation("流程图进度追踪")
    @RequestMapping(value = {"/traceProcess/{processInstanceId}"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> traceprocess(@PathVariable String processInstanceId, HttpServletResponse response) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
        ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        if (pde != null && pde.hasGraphicalNotation()) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
            ProcessDiagramGenerator diagramGenerator = configuration.getProcessDiagramGenerator();
            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", runtimeService.getActiveActivityIds(processInstance.getId()), Collections.emptyList(), "宋体", "宋体", "宋体", configuration.getClassLoader(), 1.0D);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");

            try {
                return new ResponseEntity(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
            } catch (Exception var10) {
                throw new ActivitiIllegalArgumentException("Error exporting diagram", var10);
            }
        } else {
            throw new ActivitiIllegalArgumentException("Process instance with id '" + processInstance.getId() + "' has no graphical notation defined.");
        }
    }

    @ApiOperation("挂起一个流程实例")
    @RequestMapping(value = "/suspend/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult suspend(@PathVariable String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return AjaxResult.success();
    }

    @ApiOperation("唤醒一个挂起的流程实例")
    @RequestMapping(value = "/run/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult rerun(@PathVariable String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return AjaxResult.success();
    }

    @ApiOperation("查询一个流程的变量")
    @RequestMapping(value = "/variables/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo variables(@PathVariable String processInstanceId, Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().asc().listPage(start, pageSize);
        int total = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().asc().list().size();
        List<VariableInfo> infos = new ArrayList<>();
        variables.forEach(v->{
            VariableInfo info = new VariableInfo();
            BeanUtils.copyBeanProp(info, v);
            info.setValue(v.getValue().toString());
            infos.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(infos);
        rspData.setTotal(total);
        return rspData;
    }
}
