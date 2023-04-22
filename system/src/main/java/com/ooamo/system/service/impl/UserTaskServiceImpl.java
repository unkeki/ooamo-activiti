package com.ooamo.system.service.impl;

import com.ooamo.common.utils.StringUtils;
import com.ooamo.system.service.IUserTaskService;
import com.ooamo.system.vo.UserTaskVO;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserTaskServiceImpl implements IUserTaskService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Override
    public List<UserTaskVO> findUserTaskByPdId(String processDefinitionId) {
        List<UserTaskVO> processTasks = new ArrayList<>();
        if(StringUtils.isEmpty(processDefinitionId)){
            return processTasks;
        }
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if(pd == null){
            return processTasks;
        }else{
            return findProcessUserTaskByPd(pd);
        }
    }

    @Override
    public void addUser2Task(Map<String, String> assigneeUserMap, String pdId) {

        List<UserTaskVO> userTasks = findUserTaskByPdId(pdId);
        HashMap<String, Object> variables = new HashMap<>();
        for (UserTaskVO userTaskVO : userTasks){
            String taskName = userTaskVO.getTaskName();
            String assignee = assigneeUserMap.get(taskName);
            String assigneeName = userTaskVO.getAssignee();
            variables.put(formatAssignee(assigneeName), assignee);
        }
        runtimeService.startProcessInstanceById(pdId,variables);

    }

    private  List<UserTaskVO> findProcessUserTaskByPd(ProcessDefinition pd) {
        List<UserTaskVO> processTasks = new ArrayList<>();
        String resName = pd.getResourceName();
        InputStream resourceStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), resName);
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in;
        XMLStreamReader xtr;
        try {
            in = new InputStreamReader(resourceStream, "UTF-8");
            xtr = xif.createXMLStreamReader(in);
            BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(xtr);
            Process process = model.getProcesses().get(0);
            List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
            List<StartEvent> startEvents = process.findFlowElementsOfType(StartEvent.class);
            startEvents.forEach(event -> {
                UserTaskVO userTaskVO = UserTaskVO.builder()
                        .taskId(event.getId())
                        .taskName(event.getName())
                        .assignee(event.getInitiator())
                        .processDefinitionId(pd.getId())
                        .build();
                processTasks.add(userTaskVO);
            });
            userTasks.forEach(task -> {
                UserTaskVO userTaskVO = UserTaskVO.builder()
                        .taskId(task.getId())
                        .taskName(task.getName())
                        .assignee(task.getAssignee())
                        .processDefinitionId(pd.getId())
                        .build();
                processTasks.add(userTaskVO);
            });
        } catch (XMLStreamException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return processTasks;
    }

    /**
     * 对待办人字符串进行格式化
     * @param assignee
     * @return
     */
    private String formatAssignee(String assignee){

        if (assignee.startsWith("$")){
            assignee = assignee.substring(2, assignee.indexOf("}"));
        }
        return assignee;
    }



}
