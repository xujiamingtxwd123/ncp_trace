package com.ruoyi.traces.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.traces.domain.Crop;
import com.ruoyi.traces.domain.Process;
import com.ruoyi.traces.fabric.Const;
import com.ruoyi.traces.service.BlockchainService;
import com.ruoyi.traces.service.ProcessService;
import com.ruoyi.traces.utils.JsonUtil;
import com.ruoyi.traces.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessImpl implements ProcessService {
    @Autowired
    private BlockchainService blockchainService;

    @Override
    public int saveProcess(Process process) throws Exception {
        //记录加工成商品的信息
        String result = blockchainService.send(Const.PEER0_ORG2_DOMAIN_NAME, "processing_upload",
                new String[]{process.getProductID(), TimeUtil.millSecondTimestampToFormat(), process.getPic(),
                        process.getProcess(), SecurityUtils.getUsername()});
        if (result != null && !result.equals("")) {
            throw new Exception(result);
        }

        //记录绑定关系
        if (process.getCropID() != null && !process.getCropID().equals("")) {
            result = blockchainService.send(Const.PEER0_ORG2_DOMAIN_NAME, "crop_product_bind",
                    new String[]{process.getProductID(), process.getCropID(), TimeUtil.millSecondTimestampToFormat(),
                            SecurityUtils.getUsername()});
            if (result != null && !result.equals("")) {
                throw new Exception(result);
            }
        }
        return 0;
    }

    @Override
    public List<Process> listProcess() throws Exception {
        String result = blockchainService.query(Const.PEER0_ORG2_DOMAIN_NAME, "query",
                new String[]{"name", SecurityUtils.getUsername()});

        List<Process> ti = new ArrayList<>();
        try {
            ti = JsonUtil.getObjectsFromJson(result, Process.class);
        } catch (Exception e) {
            return null;
        }

        for (int i = 0; i < ti.size(); i++) {
            ti.get(i).setCropID(getBindInfo(ti.get(i).getProductID()));
        }

        return ti;
    }

    @Override
    public String getBindInfo(String productID) throws Exception {
        String result = blockchainService.query(Const.PEER0_ORG2_DOMAIN_NAME, "query",
                new String[]{"bind", productID});
        return result;
    }

}
