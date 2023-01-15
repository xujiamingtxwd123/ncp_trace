package com.ruoyi.traces.service;

import com.ruoyi.traces.domain.Process;
import com.ruoyi.traces.domain.Transport;

import java.util.List;

public interface TransportService {
    int saveTransport(Transport transport) throws Exception;
    List<Transport> listTransport() throws Exception;
}
