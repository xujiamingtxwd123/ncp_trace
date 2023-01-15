package com.ruoyi.traces.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.traces.domain.Crop;
import com.ruoyi.traces.domain.Process;
import com.ruoyi.traces.domain.Sale;
import com.ruoyi.traces.domain.Trace;
import com.ruoyi.traces.domain.Transport;
import com.ruoyi.traces.fabric.Const;
import com.ruoyi.traces.service.BlockchainService;
import com.ruoyi.traces.service.ProcessService;
import com.ruoyi.traces.service.TraceService;
import com.ruoyi.traces.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraceImpl implements TraceService {
    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private ProcessService processService;

    @Override
    public Trace getTrace(String productID) throws Exception {
        String result = blockchainService.query(Const.PEER0_ORG4_DOMAIN_NAME, "query",
                new String[]{"id", productID});

        List<JsonNode> ti = new ArrayList<>();
        try {
            ti = JsonUtil.getObjectsFromJson(result, JsonNode.class);
        } catch (Exception e) {
            return null;
        }

        Trace trace = new Trace();
        List<Crop> cropsList = new ArrayList<>();
        List<Process> processesList = new ArrayList<>();
        List<Transport> transportsList = new ArrayList<>();
        List<Sale> saleList = new ArrayList<>();

        for (int i = 0;i < ti.size(); i++) {
            try {
                Crop crop = JsonUtil.deserialize(ti.get(i).toString(), Crop.class);
                if (crop != null) {
                    cropsList.add(crop);
                    continue;
                }
            } catch (Exception e) {}
            try {
                Process process = JsonUtil.deserialize(ti.get(i).toString(), Process.class);
                if (process != null) {
                    process.setCropID(processService.getBindInfo(process.getProductID()));;
                    processesList.add(process);
                    continue;
                }
            } catch (Exception e) {}
            try {
                Transport transport = JsonUtil.deserialize(ti.get(i).toString(), Transport.class);
                if (transport != null) {
                    transportsList.add(transport);
                    continue;
                }
            } catch (Exception e) {}
            try {
                Sale sale = JsonUtil.deserialize(ti.get(i).toString(), Sale.class);
                if (sale != null) {
                    saleList.add(sale);
                    continue;
                }

            } catch (Exception e) {}

        }

        trace.setTraceSale(saleList);
        trace.setTraceCrop(cropsList);
        trace.setTraceProcess(processesList);
        trace.setTraceTransport(transportsList);

        return trace;
    }
}
