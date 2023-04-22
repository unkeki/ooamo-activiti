package com.ooamo.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskVO {

    private String processDefinitionId;

    private  String taskName;

    private String taskId;

    private String assignee;

    private String candidateGroups;

}
