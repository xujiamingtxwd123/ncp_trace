package com.ruoyi.traces.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Crop {
    @JsonProperty(value = "CropID")
    private String CropID;
    @JsonProperty(value = "Timestamp")
    private String Timestamp;
    @JsonProperty(value = "Healthy")
    private String Healthy;
    @JsonProperty(value = "Pic")
    private String Pic;
    @JsonProperty(value = "Action")
    private String Action;
    @JsonProperty(value = "Name")
    private String Name;

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

    public String getHealthy() {
        return Healthy;
    }

    public void setHealthy(String healthy) {
        Healthy = healthy;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
