package com.ooamo.system.service;

import com.ooamo.system.domain.Form;

import java.util.List;

/**
 * 表单Service接口
 */
public interface IFormService 
{
    /**
     * 查询表单
     * 
     * @param id 表单主键
     * @return 表单
     */
    public Form selectFormById(Long id);

    /**
     * 查询表单列表
     * 
     * @param form 表单
     * @return 表单集合
     */
    public List<Form> selectFormList(Form form);

    /**
     * 新增表单
     * 
     * @param form 表单
     * @return 结果
     */
    public int insertForm(Form form);

    /**
     * 修改表单
     * 
     * @param form 表单
     * @return 结果
     */
    public int updateForm(Form form);

    /**
     * 批量删除表单
     * 
     * @param ids 需要删除的表单主键集合
     * @return 结果
     */
    public int deleteFormByIds(String ids);

    /**
     * 删除表单信息
     * 
     * @param id 表单主键
     * @return 结果
     */
    public int deleteFormById(Long id);
}
