package com.ooamo.system.mapper;

import com.ooamo.system.domain.FlowForm;

import java.util.List;

/**
 * 流程表单Mapper接口
 */
public interface FlowFormMapper 
{
    /**
     * 查询流程表单
     * 
     * @param id 流程表单主键
     * @return 流程表单
     */
    public FlowForm selectFlowFormById(String id);

    /**
     * 查询流程表单列表
     * 
     * @param flowForm 流程表单
     * @return 流程表单集合
     */
    public List<FlowForm> selectFlowFormList(FlowForm flowForm);

    /**
     * 新增流程表单
     * 
     * @param flowForm 流程表单
     * @return 结果
     */
    public int insertFlowForm(FlowForm flowForm);

    /**
     * 修改流程表单
     * 
     * @param flowForm 流程表单
     * @return 结果
     */
    public int updateFlowForm(FlowForm flowForm);

    /**
     * 删除流程表单
     * 
     * @param id 流程表单主键
     * @return 结果
     */
    public int deleteFlowFormById(String id);

    /**
     * 批量删除流程表单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFlowFormByIds(String[] ids);
}
