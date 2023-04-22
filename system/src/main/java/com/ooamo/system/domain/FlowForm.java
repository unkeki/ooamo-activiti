package com.ooamo.system.domain;

import com.ooamo.common.core.domain.BaseEntity;
import com.ooamo.system.vo.UserTaskVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程表单对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private Long formId;

    private String applyer;

    private Date applytime;

}
