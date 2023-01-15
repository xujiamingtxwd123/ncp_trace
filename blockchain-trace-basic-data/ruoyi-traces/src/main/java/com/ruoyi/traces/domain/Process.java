package com.ruoyi.traces.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Process {
    @JsonProperty(value = "ProductID")
    private String ProductID;
    @JsonProperty(value = "CropID")
    private String CropID;
    @JsonProperty(value = "Timestamp")
    private String Timestamp;
    @JsonProperty(value = "Pic")
    private String Pic;
    @JsonProperty(value = "Process")
    private String Process;
    @JsonProperty(value = "Name")
    private String Name;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getCropID() {
        return CropID;
    }

    public void setCropID(String cropID) {
        CropID = cropID;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getProcess() {
        return Process;
    }

    public void setProcess(String process) {
        Process = process;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
