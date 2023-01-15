package com.ruoyi.traces.service;

import com.ruoyi.traces.domain.Trace;

public interface TraceService {
    Trace getTrace(String productID) throws Exception;
}
