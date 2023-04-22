package com.ooamo.system.service;

import com.ooamo.system.vo.UserTaskVO;

import java.util.List;
import java.util.Map;

public interface IUserTaskService {

    List<UserTaskVO> findUserTaskByPdId(String processDefinitionId);

    void addUser2Task(Map<String, String> assigneeUserMap, String pdId);
}
