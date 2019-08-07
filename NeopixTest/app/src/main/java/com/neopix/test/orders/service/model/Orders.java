package com.neopix.test.orders.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders {
  @SerializedName("data")
  public List<Order> data;

  @SerializedName("meta")
  public Meta meta;
}
