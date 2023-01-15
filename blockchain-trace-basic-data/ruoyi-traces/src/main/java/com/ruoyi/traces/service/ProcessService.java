package com.ruoyi.traces.service;

import com.ruoyi.traces.domain.Crop;
import com.ruoyi.traces.domain.Process;

import java.util.List;

public interface ProcessService {
    int saveProcess(Process process) throws Exception;
    List<Process> listProcess() throws Exception;
    String getBindInfo(String productID) throws Exception;
}
