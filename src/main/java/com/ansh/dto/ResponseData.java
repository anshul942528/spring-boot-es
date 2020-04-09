package com.ansh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseData {

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("price")
    private double price;
}
