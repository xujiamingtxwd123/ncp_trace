package com.ruoyi.traces.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.traces.domain.Crop;
import com.ruoyi.traces.fabric.Const;
import com.ruoyi.traces.service.BlockchainService;
import com.ruoyi.traces.service.CropService;
import com.ruoyi.traces.utils.JsonUtil;
import com.ruoyi.traces.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CropImpl implements CropService {
    @Autowired
    private BlockchainService blockchainService;

    @Override
    public int saveCrops(Crop crop) throws Exception {
        String result = blockchainService.send(Const.PEER0_ORG1_DOMAIN_NAME, "crop_upload",
                new String[]{crop.getCropID(), TimeUtil.millSecondTimestampToFormat(), crop.getHealthy(), crop.getPic(),
                crop.getAction(), SecurityUtils.getUsername()});
        if (result != null && !result.equals("")) {
            throw new Exception(result);
        }
        return 0;
    }

    @Override
    public List<Crop> listCrops() throws Exception {
        String result = blockchainService.query(Const.PEER0_ORG1_DOMAIN_NAME, "query",
                new String[]{"name", SecurityUtils.getUsername()});

        List<Crop> ti = new ArrayList<>();
        try {
            ti = JsonUtil.getObjectsFromJson(result, Crop.class);
        } catch (Exception e) {
            return null;
        }
        return ti;
    }
}
