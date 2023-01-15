package com.ruoyi.traces.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sale {
    @JsonProperty(value = "ProductID")
    private String ProductID;
    @JsonProperty(value = "ConsumerName")
    private String ConsumerName;
    @JsonProperty(value = "Timestamp")
    private String Timestamp;
    @JsonProperty(value = "Name")
    private String Name;

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

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getConsumerName() {
        return ConsumerName;
    }

    public void setConsumerName(String consumerName) {
        ConsumerName = consumerName;
    }
}
