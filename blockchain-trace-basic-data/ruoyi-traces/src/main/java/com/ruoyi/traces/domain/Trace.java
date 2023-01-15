package com.ruoyi.traces.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Trace {
    @JsonProperty(value = "TraceCrop")
    private List<Crop> TraceCrop;
    @JsonProperty(value = "TraceProcess")
    private List<Process> TraceProcess;
    @JsonProperty(value = "TraceTransport")
    private List<Transport> TraceTransport;
    @JsonProperty(value = "TraceSale")
    private List<Sale> TraceSale;

    public List<Crop> getTraceCrop() {
        return TraceCrop;
    }

    public void setTraceCrop(List<Crop> traceCrop) {
        TraceCrop = traceCrop;
    }

    public List<Process> getTraceProcess() {
        return TraceProcess;
    }

    public void setTraceProcess(List<Process> traceProcess) {
        TraceProcess = traceProcess;
    }

    public List<Transport> getTraceTransport() {
        return TraceTransport;
    }

    public void setTraceTransport(List<Transport> traceTransport) {
        TraceTransport = traceTransport;
    }

    public List<Sale> getTraceSale() {
        return TraceSale;
    }

    public void setTraceSale(List<Sale> traceSale) {
        TraceSale = traceSale;
    }
}
