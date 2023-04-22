package com.ooamo.system.service.impl;

import com.ooamo.common.core.text.Convert;
import com.ooamo.system.domain.FlowForm;
import com.ooamo.system.mapper.FlowFormMapper;
import com.ooamo.system.service.IFlowFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FlowFormServiceImpl implements IFlowFormService {


    @Autowired
    private FlowFormMapper flowFormMapper;

    @Override
    public FlowForm selectFlowFormById(String id) {
        return flowFormMapper.selectFlowFormById(id);
    }

    @Override
    public List<FlowForm> selectFlowFormList(FlowForm flowForm) {
        return flowFormMapper.selectFlowFormList(flowForm);
    }

    @Override
    public int insertFlowForm(FlowForm flowForm) {

        return flowFormMapper.insertFlowForm(flowForm);
    }

    @Override
    public int updateFlowForm(FlowForm flowForm) {
        return flowFormMapper.updateFlowForm(flowForm);
    }

    @Override
    public int deleteFlowFormByIds(String ids) {
        String[] keys = Convert.toStrArray(ids);
        return flowFormMapper.deleteFlowFormByIds(keys);
    }

    @Override
    public int deleteFlowFormById(String id) {
        return flowFormMapper.deleteFlowFormById(id);
    }
}
