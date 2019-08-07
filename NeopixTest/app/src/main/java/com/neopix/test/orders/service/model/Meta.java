package com.neopix.test.orders.service.model;

import com.google.gson.annotations.SerializedName;

public class Meta {
  @SerializedName("nextId")
  public int nextId;

  @SerializedName("count")
  public int count;

  @SerializedName("totalCount")
  public int totalCount;

  @SerializedName("totalAmount")
  public int totalAmount;
}
