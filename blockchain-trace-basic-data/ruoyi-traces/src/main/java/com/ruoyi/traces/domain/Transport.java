package com.ruoyi.traces.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transport {
    @JsonProperty(value = "ProductID")
    private String ProductID;
    @JsonProperty(value = "Src")
    private String Src;
    @JsonProperty(value = "Dst")
    private String Dst;
    @JsonProperty(value = "State")
    private String State;
    @JsonProperty(value = "Timestamp")
    private String Timestamp;
    @JsonProperty(value = "Name")
    private String Name;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getSrc() {
        return Src;
    }

    public void setSrc(String src) {
        Src = src;
    }

    public String getDst() {
        return Dst;
    }

    public void setDst(String dst) {
        Dst = dst;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
