package com.ruoyi.traces.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.traces.domain.Sale;
import com.ruoyi.traces.domain.Transport;
import com.ruoyi.traces.fabric.Const;
import com.ruoyi.traces.service.BlockchainService;
import com.ruoyi.traces.service.SaleService;
import com.ruoyi.traces.service.TransportService;
import com.ruoyi.traces.utils.JsonUtil;
import com.ruoyi.traces.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleImpl implements SaleService {
    @Autowired
    private BlockchainService blockchainService;

    @Override
    public int saveSale(Sale sale) throws Exception {
        String result = blockchainService.send(Const.PEER0_ORG4_DOMAIN_NAME, "sale_upload",
                new String[]{sale.getProductID(), TimeUtil.millSecondTimestampToFormat(),
                        sale.getConsumerName(), SecurityUtils.getUsername()});
        if (result != null && !result.equals("")) {
            throw new Exception(result);
        }
        return 0;
    }

    @Override
    public List<Sale> listSale() throws Exception {
        String result = blockchainService.query(Const.PEER0_ORG4_DOMAIN_NAME, "query",
                new String[]{"name", SecurityUtils.getUsername()});

        List<Sale> ti = new ArrayList<>();
        try {
            ti = JsonUtil.getObjectsFromJson(result, Sale.class);
        } catch (Exception e) {
            return null;
        }
        return ti;
    }
}
