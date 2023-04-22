package com.ooamo.system.mapper;

import com.ooamo.system.domain.Form;

import java.util.List;

/**
 * 表单Mapper接口
 */
public interface FormMapper 
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
     * 删除表单
     * 
     * @param id 表单主键
     * @return 结果
     */
    public int deleteFormById(Long id);

    /**
     * 批量删除表单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFormByIds(String[] ids);
}
