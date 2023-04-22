package com.ooamo.system.service.impl;

import com.ooamo.common.core.text.Convert;
import com.ooamo.system.domain.Form;
import com.ooamo.system.mapper.FormMapper;
import com.ooamo.system.service.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FormServiceImpl implements IFormService {


    @Autowired
    private FormMapper formMapper;

    @Override
    public Form selectFormById(Long id) {
        return formMapper.selectFormById(id);
    }

    @Override
    public List<Form> selectFormList(Form form) {
        return formMapper.selectFormList(form);
    }

    @Override
    public int insertForm(Form form) {

        return formMapper.insertForm(form);
    }

    @Override
    public int updateForm(Form form) {
        return formMapper.updateForm(form);
    }

    @Override
    public int deleteFormByIds(String ids) {
        String[] keys = Convert.toStrArray(ids);
        return formMapper.deleteFormByIds(keys);
    }

    @Override
    public int deleteFormById(Long id) {
        return formMapper.deleteFormById(id);
    }
}
