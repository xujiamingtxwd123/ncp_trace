package com.ruoyi.traces.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.traces.domain.Process;
import com.ruoyi.traces.domain.Transport;
import com.ruoyi.traces.fabric.Const;
import com.ruoyi.traces.service.BlockchainService;
import com.ruoyi.traces.service.ProcessService;
import com.ruoyi.traces.service.TransportService;
import com.ruoyi.traces.utils.JsonUtil;
import com.ruoyi.traces.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransportImpl implements TransportService {
    @Autowired
    private BlockchainService blockchainService;

    @Override
    public int saveTransport(Transport transport) throws Exception {
        String result = blockchainService.send(Const.PEER0_ORG3_DOMAIN_NAME, "transport_upload",
                new String[]{transport.getProductID(), transport.getSrc(), transport.getDst(),
                        transport.getState(), TimeUtil.millSecondTimestampToFormat(), SecurityUtils.getUsername()});
        if (result != null && !result.equals("")) {
            throw new Exception(result);
        }
        return 0;
    }

    @Override
    public List<Transport> listTransport() throws Exception {
        String result = blockchainService.query(Const.PEER0_ORG3_DOMAIN_NAME, "query",
                new String[]{"name", SecurityUtils.getUsername()});

        List<Transport> ti = new ArrayList<>();
        try {
            ti = JsonUtil.getObjectsFromJson(result, Transport.class);
        } catch (Exception e) {
            return null;
        }
        return ti;
    }
}
